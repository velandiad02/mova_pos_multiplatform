package com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source

import com.example.mova_pos_multiplatform.core.common.utils.runLocalCatching
import com.example.mova_pos_multiplatform.feature.transactions.data.local.dao.TransactionDao
import com.example.mova_pos_multiplatform.feature.transactions.data.local.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.transactions.data.local.mapper.toEntity
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

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

    override suspend fun getTransactionsByTerminalId(terminalId: String?): Result<List<Transaction>> =
        runLocalCatching {
            val transactions = terminalId?.let { transactionDao.getTransactionsByTerminalId(terminalId = it) }
                ?: transactionDao.getTransactionsByTerminalId()

            transactions.map { it.toDomain() }
        }

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