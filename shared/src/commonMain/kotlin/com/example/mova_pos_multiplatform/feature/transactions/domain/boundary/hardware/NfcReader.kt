package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money

interface NfcReader {

    suspend fun read(amount: Money): ChannelResult
}