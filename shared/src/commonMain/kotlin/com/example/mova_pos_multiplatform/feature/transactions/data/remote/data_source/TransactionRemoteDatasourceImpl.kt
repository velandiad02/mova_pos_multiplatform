package com.example.mova_pos_multiplatform.feature.transactions.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.transactions.data.remote.api.TransactionApi
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.mapper.toRequestDto
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

class TransactionRemoteDatasourceImpl(
    private val transactionApi: TransactionApi,
) : TransactionRemoteDatasource {

    override suspend fun createTransaction(transaction: Transaction): Result<TransactionStatus> =
        runCatching {
            val status = transactionApi.createTransaction(
                transaction = transaction.toRequestDto(),
            ).status

            TransactionStatus.valueOf(value = status)
        }
}