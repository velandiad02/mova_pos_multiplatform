package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce

interface CommerceRemoteDataSource {

    suspend fun fetchCommerces(): Result<List<Commerce>>
}