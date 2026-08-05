package com.example.mova_pos_multiplatform.feature.transactions.presentation.history

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GetTransactionsByTerminalUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.RetryPendingTransactionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DefaultHistoryComponent(
    componentContext: ComponentContext,
    private val terminalId: String,
    private val observeTransactionsUseCase: GetTransactionsByTerminalUseCase,
    private val retrySyncTransactionsUseCase: RetryPendingTransactionsUseCase,
    private val onNavigateToDetail: (Transaction) -> Unit,
) : HistoryComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(context = Dispatchers.Main.immediate)
    private var observeTransactionsJob: Job? = null

    private val _model = MutableValue(initialValue = HistoryComponent.Model())
    override val model: Value<HistoryComponent.Model> = _model

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        observeTransactionsJob?.cancel()
        observeTransactionsJob = scope.launch {
            observeTransactionsUseCase(terminalId = terminalId).collect { transactions ->
                _model.update { it.copy(transactions = transactions) }
            }
        }
    }

    override fun onRetrySyncClicked() {
        if (_model.value.isSyncing) return

        _model.update { it.copy(isSyncing = true, errorMessage = null) }

        scope.launch {
            retrySyncTransactionsUseCase(terminalId = terminalId)
                .onSuccess {
                    _model.update { it.copy(isSyncing = false) }
                }
                .onFailure { error ->
                    _model.update {
                        it.copy(
                            isSyncing = false,
                            errorMessage = error.message ?: "Error al sincronizar transacciones."
                        )
                    }
                }
        }
    }

    override fun onTransactionClicked(transaction: Transaction) {
        onNavigateToDetail(transaction)
    }
}