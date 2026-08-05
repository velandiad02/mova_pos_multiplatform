package com.example.mova_pos_multiplatform.feature.transactions.presentation.history

import com.arkivanov.decompose.value.Value
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

interface HistoryComponent {

    val model: Value<Model>

    data class Model(
        val transactions: List<Transaction> = emptyList(),
        val isSyncing: Boolean = false,
        val errorMessage: String? = null,
    ) {

        val hasFailedTransactions: Boolean
            get() = transactions.any { it.status == TransactionStatus.FAILED }
    }

    fun onRetrySyncClicked()
    fun onTransactionClicked(id: String)
}