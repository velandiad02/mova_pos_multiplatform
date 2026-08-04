package com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    val status: String,
)