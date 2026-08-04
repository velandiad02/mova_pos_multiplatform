package com.example.mova_pos_multiplatform.feature.transactions.domain.model

sealed interface ChannelResult {

    object Success : ChannelResult

    object CancelledByUser : ChannelResult
    object Timeout : ChannelResult
    object HardwareNotAvailable : ChannelResult

    data class UnknownError(val message: String) : ChannelResult
}