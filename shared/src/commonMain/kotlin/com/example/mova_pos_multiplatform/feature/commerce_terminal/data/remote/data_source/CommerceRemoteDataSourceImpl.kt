package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api.CommerceApi
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce

class CommerceRemoteDataSourceImpl(
    private val commerceApi: CommerceApi,
) : CommerceRemoteDataSource {

    override suspend fun fetchCommerces(): Result<List<Commerce>> = runCatching {
        commerceApi.fetchCommerces().map { it.toDomain() }
    }
}