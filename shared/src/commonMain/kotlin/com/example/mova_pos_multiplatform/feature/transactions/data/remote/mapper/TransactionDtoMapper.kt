package com.example.mova_pos_multiplatform.feature.transactions.data.remote.mapper

import com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto.MoneyRequestDto
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.dto.TransactionRequestDto
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

fun Transaction.toRequestDto(): TransactionRequestDto = TransactionRequestDto(
    idempotencyKey = idempotencyKey,
    terminalId = terminalId,
    amount = amount.toRequestDto(),
    channel = channel.name,
    createdAt = createdAt,
)

fun Money.toRequestDto(): MoneyRequestDto = MoneyRequestDto(
    amountInMinimumUnit = amountInMinimumUnit,
    currency = currency,
)