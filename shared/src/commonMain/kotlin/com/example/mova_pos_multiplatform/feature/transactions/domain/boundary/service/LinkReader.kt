package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.service

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money

interface LinkReader {

    suspend fun generateAndRead(amount: Money): ChannelResult
}