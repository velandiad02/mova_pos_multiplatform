package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository

class SyncTransactionsUseCase(
    private val repository: TransactionRepository,
) {

    suspend operator fun invoke(terminalId: String, mustSaveErrors: Boolean): Result<Unit> {
        repository.unMarkTransactionsAsFailed(terminalId = terminalId)
        var finalException: AppException? = null

        while (true) {
            val pendingTransactionsResult = repository.getPendingTransactions(
                terminalId = terminalId,
            )

            if (pendingTransactionsResult.isFailure) {
                finalException = pendingTransactionsResult.exceptionOrNull() as AppException
                break
            }

            val pendingTransactions = pendingTransactionsResult.getOrNull()
            if (pendingTransactions.isNullOrEmpty()) {
                break
            }

            for (transaction in pendingTransactions) {
                val syncResult = repository.syncTransaction(
                    transaction = transaction,
                    mustSaveErrors = mustSaveErrors,
                )

                if (syncResult.isFailure) {
                    val currentException = syncResult.exceptionOrNull() as? AppException

                    if (currentException?.status == ErrorStatus.RETRYABLE || finalException == null) {
                        finalException = currentException
                    }
                }
            }

            if (finalException != null) {
                break
            }
        }

        return if (finalException == null) {
            Result.success(value = Unit)
        } else {
            Result.failure(exception = finalException)
        }
    }
}