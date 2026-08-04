package com.example.mova_pos_multiplatform.feature.transactions.domain.scheduler

expect class TransactionsSyncSchedulerLauncher {

    fun launchScheduler(terminalId: String)
}