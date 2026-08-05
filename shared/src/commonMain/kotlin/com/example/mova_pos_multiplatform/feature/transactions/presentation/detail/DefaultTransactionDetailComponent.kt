package com.example.mova_pos_multiplatform.feature.transactions.presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PrinterResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GenerateReceiptUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.PrintReceptUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultTransactionDetailComponent(
    componentContext: ComponentContext,
    private val transactionId: String,
//    private val observeTransactionDetailUseCase: ObserveTransactionDetailUseCase,
    private val generateReceiptUseCase: GenerateReceiptUseCase,
    private val printReceiptUseCase: PrintReceptUseCase,
) : TransactionDetailComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(context = Dispatchers.Main.immediate)
    private var observeJob: Job? = null

    private val _model = MutableValue(initialValue = TransactionDetailComponent.Model())
    override val model: Value<TransactionDetailComponent.Model> = _model

    init {
        loadTransaction()
    }

    private fun loadTransaction() {
        observeJob?.cancel()
        observeJob = scope.launch {
//            observeTransactionDetailUseCase(transactionId).collect { transaction ->
//                _model.update { it.copy(transaction = transaction, isLoading = false) }
//            }
        }
    }

    override fun onPrintReceiptClicked() {
        val currentTransaction = _model.value.transaction ?: return
        if (_model.value.isPrinting) return

        _model.update { it.copy(isPrinting = true, printerMessage = null, isPrintError = false) }

        scope.launch {
            val receiptResult = generateReceiptUseCase(currentTransaction)

            receiptResult.onSuccess { receipt ->
                val (message, isPrintError) = when (printReceiptUseCase(receipt)) {
                    is PrinterResult.Success -> {
                        "Recibo impreso exitosamente." to false
                    }
                    is PrinterResult.PrinterNotConnected -> {
                        "No hay impresora conectada" to true
                    }
                    is PrinterResult.OutOfPaper -> {
                        "No hay papel en la impresora" to true
                    }
                }

                _model.update {
                    it.copy(
                        isPrinting = false,
                        printerMessage = message,
                        isPrintError = isPrintError,
                    )
                }
            }.onFailure { error ->
                _model.update {
                    it.copy(
                        isPrinting = false,
                        printerMessage = "Error al generar recibo: ${error.message}",
                        isPrintError = true,
                    )
                }
            }
        }
    }
}