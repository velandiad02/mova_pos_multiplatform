package com.example.mova_pos_multiplatform.core.common.utils

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money

fun simulateResultByAmount(amount: Money): ChannelResult {
    val lastDigit = amount.amountInMinimumUnit % 10L

    return when (lastDigit) {
        1L -> ChannelResult.Timeout
        2L -> ChannelResult.CancelledByUser
        3L -> ChannelResult.HardwareNotAvailable
        else -> ChannelResult.Success
    }
}