package com.example.mova_pos_multiplatform.feature.transactions.domain.model

sealed interface PrinterResult {
    object Success : PrinterResult
    object OutOfPaper : PrinterResult
    object PrinterNotConnected : PrinterResult
}