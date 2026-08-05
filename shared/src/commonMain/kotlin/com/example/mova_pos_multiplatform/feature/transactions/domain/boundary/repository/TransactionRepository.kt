package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

interface TransactionRepository {

    suspend fun getPendingTransactions(terminalId: String): Result<List<Transaction>>

    suspend fun getTransactionsByTerminalId(terminalId: String? = null): Result<List<Transaction>>

    suspend fun saveTransaction(transaction: Transaction): Result<Unit>

    suspend fun updateTransaction(transaction: Transaction): Result<Unit>

    suspend fun unMarkTransactionsAsFailed(terminalId: String): Result<Unit>

    suspend fun syncTransaction(transaction: Transaction, mustSaveErrors: Boolean): Result<Unit>
}