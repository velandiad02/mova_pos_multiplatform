package com.example.mova_pos_multiplatform.feature.transactions.domain.model

data class Receipt(
    val idempotencyKey: String,
    val commerceName: String,
    val terminalName: String,
    val amountFormatted: String,
    val dateFormatted: String,
    val channel: PaymentChannel,
    val status: TransactionStatus,
)