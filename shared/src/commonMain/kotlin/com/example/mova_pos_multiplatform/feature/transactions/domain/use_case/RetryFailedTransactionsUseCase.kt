package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler.TransactionsSyncSchedulerLauncher

class RetryPendingTransactionsUseCase(
    private val repository: TransactionRepository,
    private val scheduler: TransactionsSyncSchedulerLauncher,
) {

    suspend operator fun invoke(terminalId: String): Result<Unit> {
        val result = repository.unMarkTransactionsAsFailed(terminalId = terminalId)

        return result.fold(
            onSuccess = {
                scheduler.launchScheduler(terminalId = terminalId)
                Result.success(Unit)
            },
            onFailure = { Result.failure(exception = it) },
        )
    }
}
