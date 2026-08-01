package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow

interface TerminalRepository {

    fun observeTerminalsByCommerceId(commerceId: String): Flow<List<Terminal>>

    suspend fun syncTerminalsForCommerce(commerceId: String): Result<Unit>

    suspend fun deleteAllTerminals(): Result<Unit>
}