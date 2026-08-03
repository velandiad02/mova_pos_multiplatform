package com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncWorkManager

actual class TransactionsSyncSchedulerLauncher(
    private val transactionsSyncWorkManager: TransactionsSyncWorkManager,
) {

    actual fun launchScheduler(terminalId: String) {
        transactionsSyncWorkManager.enqueueSync(terminalId = terminalId)
    }
}