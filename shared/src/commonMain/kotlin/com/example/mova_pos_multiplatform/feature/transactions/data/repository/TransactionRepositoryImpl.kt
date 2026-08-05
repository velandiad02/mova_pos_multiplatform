package com.example.mova_pos_multiplatform.feature.transactions.data.repository

import com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source.TransactionLocalDataSource
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.data_source.TransactionRemoteDatasource
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TransactionRepositoryImpl(
    private val localDataSource: TransactionLocalDataSource,
    private val remoteDatasource: TransactionRemoteDatasource,
    private val ioDispatcher: CoroutineContext,
) : TransactionRepository {

    override suspend fun getPendingTransactions(terminalId: String): Result<List<Transaction>> =
        withContext(context = ioDispatcher) {
            localDataSource.getPendingTransactions(terminalId = terminalId)
        }

    override fun observeTransactionsByTerminalId(terminalId: String): Flow<List<Transaction>> =
        localDataSource.observeTransactionsByTerminalId(terminalId = terminalId)

    override suspend fun saveTransaction(transaction: Transaction): Result<Unit> =
        withContext(context = ioDispatcher) {
            localDataSource.saveTransaction(transaction = transaction)
        }

    override suspend fun updateTransaction(transaction: Transaction): Result<Unit> =
        withContext(context = ioDispatcher) {
            localDataSource.updateTransaction(transaction = transaction)
        }

    override suspend fun unMarkTransactionsAsFailed(terminalId: String): Result<Unit> =
        withContext(context = ioDispatcher) {
            localDataSource.unMarkTransactionsAsFailed(terminalId = terminalId)
        }

    override suspend fun syncTransaction(
        transaction: Transaction,
        mustSaveErrors: Boolean,
    ): Result<TransactionStatus> = withContext(context = ioDispatcher) {
        val result = remoteDatasource.createTransaction(transaction = transaction)

        result.fold(
            onSuccess = {
                localDataSource.syncTransaction(transaction = transaction, status = it)
                Result.success(value = it)
            },
            onFailure = {
                if (mustSaveErrors) {
                    localDataSource.updateTransaction(
                        transaction = transaction.copy(
                            status = TransactionStatus.FAILED,
                            errorMessage = it.message!!,
                        ),
                    )
                }

                Result.failure(exception = it)
            },
            )
        }
}