package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler.TransactionsSyncSchedulerLauncher
import java.util.UUID

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val scheduler: TransactionsSyncSchedulerLauncher,
) {

    suspend operator fun invoke(
        terminalId: String,
        amountInMinimumUnit: Long,
        channel: PaymentChannel,
    ): Result<Unit> {
        val transaction = createInstanceOfTransaction(
            terminalId = terminalId,
            amountInMinimumUnit = amountInMinimumUnit,
            channel = channel,
        )

        val result = transactionRepository.saveTransaction(transaction = transaction)

        return result.fold(
            onSuccess = { syncTransaction(transaction = transaction) },
            onFailure = { Result.failure(exception = it) }
        )
    }

    suspend fun syncTransaction(transaction: Transaction): Result<Unit> {
        val syncResult = transactionRepository.syncTransaction(
            transaction = transaction,
            mustSaveErrors = false,
        )

        return syncResult.fold(
            onSuccess = {
                scheduler.launchScheduler(terminalId = transaction.terminalId)
                Result.success(value = Unit)
            },
            onFailure = {
                if ((it as? AppException)?.status == ErrorStatus.RETRYABLE) {
                    scheduler.launchScheduler(terminalId = transaction.terminalId)
                } else {
                    transactionRepository.updateTransaction(
                        transaction = transaction.copy(status = TransactionStatus.REJECTED),
                    )
                }

                Result.failure(exception = it)
            }
        )
    }

    private fun createInstanceOfTransaction(
        terminalId: String,
        amountInMinimumUnit: Long,
        channel: PaymentChannel,
    ): Transaction = Transaction(
        idempotencyKey = UUID.randomUUID().toString(),
        terminalId = terminalId,
        amount = Money(amountInMinimumUnit = amountInMinimumUnit),
        channel = channel,
        status = TransactionStatus.PENDING_SYNC,
        createdAt = System.currentTimeMillis(),
    )
}