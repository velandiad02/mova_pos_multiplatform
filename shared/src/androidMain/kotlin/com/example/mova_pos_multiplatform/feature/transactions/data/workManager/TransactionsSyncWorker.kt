package com.example.mova_pos_multiplatform.feature.transactions.data.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mova_pos_multiplatform.core.common.error.AppException
import com.example.mova_pos_multiplatform.core.common.error.ErrorStatus
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.SyncTransactionsUseCase

class TransactionsSyncWorker(
    private val context: Context,
    private val workerParams: WorkerParameters,
    private val syncTransactionsUseCase: SyncTransactionsUseCase,
) : CoroutineWorker(appContext = context, params = workerParams) {

    companion object {
        private const val MAX_RETRIES = 3
        const val TERMINAL_ID_KEY = "TERMINAL_ID_KEY"
    }

    override suspend fun doWork(): Result {
        val terminalId = inputData.getString(TERMINAL_ID_KEY) ?: return Result.failure()
        val hasRetries = runAttemptCount < MAX_RETRIES

        val result = syncTransactionsUseCase(terminalId = terminalId, mustSaveErrors = !hasRetries)

        return if (result.isSuccess) {
            Result.success()
        } else {
            val exception: AppException? = result.exceptionOrNull() as? AppException

            if (hasRetries && exception?.status == ErrorStatus.RETRYABLE) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
}