package com.example.mova_pos_multiplatform.core.common.extension

import androidx.compose.ui.graphics.Color
import com.example.mova_pos_multiplatform.core.designsystem.MovaStatusColors
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.TransactionStatus

fun TransactionStatus.getTitleToShow(): String {
    return when (this) {
        TransactionStatus.APPROVED -> "Aprobado"
        TransactionStatus.REJECTED -> "Rechazado"
        TransactionStatus.PENDING_SYNC -> "Sinc. Pend."
        TransactionStatus.FAILED -> "Fallido"
    }
}

fun TransactionStatus.getColor(): Color {
    return when (this) {
        TransactionStatus.APPROVED -> MovaStatusColors.completed
        TransactionStatus.REJECTED, TransactionStatus.FAILED -> MovaStatusColors.failed
        TransactionStatus.PENDING_SYNC -> MovaStatusColors.pendingSync
    }
}

fun PaymentChannel.getTitleToShow(): String {
    return when (this) {
        PaymentChannel.NFC -> "NFC"
        PaymentChannel.QR -> "QR"
        PaymentChannel.PAYMENT_LINK -> "LINK"
    }
}