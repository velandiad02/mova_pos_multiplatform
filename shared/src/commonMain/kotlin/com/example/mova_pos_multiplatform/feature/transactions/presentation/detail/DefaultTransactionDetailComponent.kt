package com.example.mova_pos_multiplatform.feature.transactions.presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PrinterResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GenerateReceiptUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.PrintReceptUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultTransactionDetailComponent(
    componentContext: ComponentContext,
    private val transaction: Transaction,
    private val generateReceiptUseCase: GenerateReceiptUseCase,
    private val printReceiptUseCase: PrintReceptUseCase,
    private val onNavigateBack: () -> Unit,
) : TransactionDetailComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(context = Dispatchers.Main.immediate)

    private val _model = MutableValue(
        initialValue = TransactionDetailComponent.Model(transaction = transaction),
    )
    override val model: Value<TransactionDetailComponent.Model> = _model

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

    override fun onBackClicked() {
        onNavigateBack()
    }
}