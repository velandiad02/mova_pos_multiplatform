package com.example.mova_pos_multiplatform.feature.transactions.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val idempotencyKey: String,
    val terminalId: String,
    @Embedded val amount: Money,
    val channel: PaymentChannel,
    val status: TransactionStatus,
    val createdAt: Long,
    val errorMessage: String? = null,
)