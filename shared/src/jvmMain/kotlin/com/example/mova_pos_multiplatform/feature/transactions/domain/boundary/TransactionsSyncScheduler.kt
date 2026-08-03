package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary

interface TransactionsSyncScheduler {

    fun scheduleSync(terminalId: String)
}