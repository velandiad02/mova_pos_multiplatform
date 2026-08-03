package com.example.mova_pos_multiplatform.feature.transactions.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

interface TransactionRemoteDatasource {

    suspend fun createTransaction(transaction: Transaction): Result<TransactionStatus>
}