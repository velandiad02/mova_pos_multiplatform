package com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source

import com.example.mova_pos_multiplatform.core.common.utils.runLocalCatching
import com.example.mova_pos_multiplatform.feature.transactions.data.local.dao.TransactionDao
import com.example.mova_pos_multiplatform.feature.transactions.data.local.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.transactions.data.local.mapper.toEntity
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class TransactionLocalDataSourceImpl(
    private val transactionDao: TransactionDao,
) : TransactionLocalDataSource {

    override suspend fun getPendingTransactions(terminalId: String): Result<List<Transaction>> =
        runLocalCatching {
            transactionDao.getTransactionsBySyncStatus(
                status = TransactionStatus.PENDING_SYNC,
                terminalId = terminalId,
            ).map { it.toDomain() }
        }

    override fun observeTransactionsByTerminalId(terminalId: String): Flow<List<Transaction>> =
        transactionDao
            .observeTransactionsByTerminalId(terminalId = terminalId)
            .map { entities -> entities.map { it.toDomain() } }
            .catch { emit(value = emptyList()) }

    override suspend fun saveTransaction(transaction: Transaction): Result<Unit> =
        runLocalCatching {
            transactionDao.saveTransaction(transaction = transaction.toEntity())
        }

    override suspend fun syncTransaction(
        transaction: Transaction,
        status: TransactionStatus,
    ): Result<Unit> = runLocalCatching {
        transactionDao.updateTransaction(transaction = transaction.toEntity().copy(status = status))
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Unit> =
        runLocalCatching{
            transactionDao.updateTransaction(transaction = transaction.toEntity())
        }

    override suspend fun unMarkTransactionsAsFailed(terminalId: String): Result<Unit> =
        runLocalCatching {
            val failedTransactions = transactionDao.getTransactionsBySyncStatus(
                status = TransactionStatus.FAILED,
                terminalId = terminalId,
            )

            transactionDao.updateTransactions(
                transactions = failedTransactions.map {
                    it.copy(status = TransactionStatus.PENDING_SYNC, errorMessage = null)
                }
            )
        }
}