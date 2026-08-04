package com.example.mova_pos_multiplatform.feature.transactions.domain.model

data class Money(
    val amountInMinimumUnit: Long,
    val currency: String = "COP",
)