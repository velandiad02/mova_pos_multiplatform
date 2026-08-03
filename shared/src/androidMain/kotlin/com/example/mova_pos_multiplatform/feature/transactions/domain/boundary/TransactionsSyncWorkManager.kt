package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary

interface TransactionsSyncWorkManager {

    fun enqueueSync(terminalId: String)
}