package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal

interface TerminalRemoteDataSource {

    suspend fun fetchTerminalsByCommerceId(commerceId: String): Result<List<Terminal>>
}