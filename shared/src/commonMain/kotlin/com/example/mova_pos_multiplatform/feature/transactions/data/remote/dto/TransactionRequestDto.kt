package com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequestDto(
    @SerialName(value = "idempotency_key")
    val idempotencyKey: String,
    @SerialName(value = "terminal_id")
    val terminalId: String,
    val amount: MoneyRequestDto,
    val channel: String,
    @SerialName(value = "created_at")
    val createdAt: Long,
)