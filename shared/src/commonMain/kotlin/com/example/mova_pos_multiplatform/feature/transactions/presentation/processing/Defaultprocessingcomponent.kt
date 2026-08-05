package com.example.mova_pos_multiplatform.feature.transactions.presentation.processing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.CreateTransactionUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.ReadTransactionChannelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DefaultProcessingComponent(
    componentContext: ComponentContext,
    private val terminalId: String,
    private val amountInMinimumUnit: Long,
    private val channel: PaymentChannel,
    private val readTransactionChannel: ReadTransactionChannelUseCase,
    private val createTransaction: CreateTransactionUseCase,
    private val onNavigateBackToChannelSelection: () -> Unit,
    private val onFinishToMainNavigation: () -> Unit,
) : ProcessingComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Main.immediate)

    private val _model = MutableValue<ProcessingComponent.Model>(ProcessingComponent.Model.ReadingChannel)
    override val model: Value<ProcessingComponent.Model> = _model

    init {
        processPayment()
    }

    private fun processPayment() {
        scope.launch {
            _model.value = ProcessingComponent.Model.ReadingChannel

            val channelResult = readTransactionChannel(
                channel = channel,
                amount = Money(amountInMinimumUnit = amountInMinimumUnit),
            )

            when (channelResult) {
                ChannelResult.Success -> createAndSyncTransaction()
                else -> onNavigateBackToChannelSelection()
            }
        }
    }

    private suspend fun createAndSyncTransaction() {
        _model.value = ProcessingComponent.Model.CreatingTransaction

        val result = createTransaction(
            terminalId = terminalId,
            amountInMinimumUnit = amountInMinimumUnit,
            channel = channel,
        )

        result.fold(
            onSuccess = {
                showSuccessAndFinish(message = "Pago aprobado")
            },
            onFailure = { exception ->
                val status = (exception as? AppException)?.status

                if (status == ErrorStatus.RETRYABLE) {
                    showSuccessAndFinish(message = "Guardado, se sincronizará automáticamente")
                } else {
                    _model.value = ProcessingComponent.Model.Error(
                        message = exception.message ?: "No se pudo guardar la transacción",
                    )
                }
            },
        )
    }

    private suspend fun showSuccessAndFinish(message: String) {
        _model.value = ProcessingComponent.Model.Success(message)
        delay(SUCCESS_MESSAGE_DURATION_MS)
        onFinishToMainNavigation()
    }

    override fun onRetryClicked() {
        onFinishToMainNavigation()
    }

    override fun onCancelClicked() {
        onFinishToMainNavigation()
    }

    private companion object {
        const val SUCCESS_MESSAGE_DURATION_MS = 1500L
    }
}