package com.example.mova_pos_multiplatform.feature.transactions.data.local.mapper

import com.example.mova_pos_multiplatform.feature.transactions.data.local.entity.TransactionEntity
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction = Transaction(
    idempotencyKey = idempotencyKey,
    terminalId = terminalId,
    amount = amount,
    channel = channel,
    status = status,
    createdAt = createdAt,
    errorMessage = errorMessage,
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    idempotencyKey = idempotencyKey,
    terminalId = terminalId,
    amount = amount,
    channel = channel,
    status = status,
    createdAt = createdAt,
    errorMessage = errorMessage,
)