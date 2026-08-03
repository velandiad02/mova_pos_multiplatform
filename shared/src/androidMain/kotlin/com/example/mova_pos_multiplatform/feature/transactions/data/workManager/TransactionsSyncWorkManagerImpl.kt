package com.example.mova_pos_multiplatform.feature.transactions.data.workManager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncWorkManager
import java.util.concurrent.TimeUnit

class TransactionsSyncWorkManagerImpl(
    private val context: Context,
): TransactionsSyncWorkManager {

    companion object {
        private const val SYNC_WORK_NAME = "PaymentSyncWork"
    }

    override fun enqueueSync(terminalId: String) {
        val inputData = workDataOf(TransactionsSyncWorker.TERMINAL_ID_KEY to terminalId)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = OneTimeWorkRequestBuilder<TransactionsSyncWorker>()
            .setConstraints(constraints = constraints)
            .setInputData(inputData = inputData)
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = WorkRequest.MIN_BACKOFF_MILLIS,
                timeUnit = TimeUnit.MILLISECONDS,
            )
            .build()

        WorkManager.getInstance(context = context).enqueueUniqueWork(
            uniqueWorkName = SYNC_WORK_NAME,
            existingWorkPolicy = ExistingWorkPolicy.KEEP,
            request = syncWorkRequest,
        )

    }
}