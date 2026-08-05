package com.example.mova_pos_multiplatform.core.common.utils

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money

fun simulateResultByAmount(amount: Money): ChannelResult {
    return when (amount.amountInMinimumUnit) {
        1500100L -> ChannelResult.Timeout
        1500200L -> ChannelResult.CancelledByUser
        1500300L -> ChannelResult.HardwareNotAvailable
        else -> ChannelResult.Success
    }
}