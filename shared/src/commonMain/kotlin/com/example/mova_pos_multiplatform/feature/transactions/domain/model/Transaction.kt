package com.example.mova_pos_multiplatform.feature.transactions.domain.model

data class Transaction(
    val idempotencyKey: String,
    val terminalId: String,
    val amount: Money,
    val channel: PaymentChannel,
    val status: TransactionStatus,
    val createdAt: Long,
    val errorMessage: String? = null,
)