package com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus
import kotlinx.coroutines.flow.Flow

interface TransactionLocalDataSource {

    suspend fun getPendingTransactions(terminalId: String): Result<List<Transaction>>

    fun observeTransactionsByTerminalId(terminalId: String): Flow<List<Transaction>>

    suspend fun saveTransaction(transaction: Transaction): Result<Unit>

    suspend fun syncTransaction(transaction: Transaction, status: TransactionStatus): Result<Unit>

    suspend fun updateTransaction(transaction: Transaction): Result<Unit>

    suspend fun unMarkTransactionsAsFailed(terminalId: String): Result<Unit>

}