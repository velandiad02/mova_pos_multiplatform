package com.example.mova_pos_multiplatform.feature.transactions.domain.model

enum class TransactionStatus {
    APPROVED,
    REJECTED,
    PENDING_SYNC,
    FAILED;
}