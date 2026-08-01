package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow

interface CommerceRepository {

    fun observeCommerces(): Flow<List<Commerce>>

    suspend fun refreshCommerces(forceRefresh: Boolean): Result<Unit>
}