package com.example.mova_pos_multiplatform.feature.transactions.data.service

import com.example.mova_pos_multiplatform.core.common.utils.simulateResultByAmount
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.service.LinkReader
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import kotlinx.coroutines.delay

class FakeLinkReaderImpl : LinkReader {

    override suspend fun generateAndRead(amount: Money): ChannelResult {
        delay(timeMillis = 2000L)

        return simulateResultByAmount(amount = amount)
    }
}