package com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.TransactionsSyncScheduler

actual class TransactionsSyncSchedulerLauncher(
    private val transactionsSyncScheduler: TransactionsSyncScheduler,
) {

    actual fun launchScheduler(terminalId: String) {
        transactionsSyncScheduler.scheduleSync(terminalId = terminalId)
    }
}