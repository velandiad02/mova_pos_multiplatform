package com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class MoneyRequestDto(
    val amountInMinimumUnit: Long,
    val currency: String,
)