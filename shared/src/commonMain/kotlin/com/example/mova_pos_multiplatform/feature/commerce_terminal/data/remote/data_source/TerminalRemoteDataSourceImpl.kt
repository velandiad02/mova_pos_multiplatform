package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api.TerminalApi
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal

class TerminalRemoteDataSourceImpl(
    private val terminalApi: TerminalApi,
) : TerminalRemoteDataSource {

    override suspend fun fetchTerminalsByCommerceId(commerceId: String): Result<List<Terminal>> =
        runCatching {
            terminalApi.fetchTerminalsByCommerceId(commerceId = commerceId).map { it.toDomain() }
        }
}