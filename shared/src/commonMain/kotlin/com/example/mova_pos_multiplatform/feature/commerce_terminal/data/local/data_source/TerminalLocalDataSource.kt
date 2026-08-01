package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow

interface TerminalLocalDataSource {

    fun observeTerminalsByCommerceId(commerceId: String): Flow<List<Terminal>>

    suspend fun isEmpty(commerceId: String): Result<Boolean>

    suspend fun replaceTerminalsByCommerce(
        commerceId: String,
        terminals: List<Terminal>,
    ): Result<Unit>

    suspend fun deleteAllTerminals(): Result<Unit>
}