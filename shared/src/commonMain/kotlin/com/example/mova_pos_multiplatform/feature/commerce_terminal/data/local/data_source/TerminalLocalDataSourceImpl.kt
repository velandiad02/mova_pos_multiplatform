package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source

import com.example.mova_pos_multiplatform.core.common.runLocalCatching
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.TerminalDao
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper.toEntity
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class TerminalLocalDataSourceImpl(
    private val terminalDao: TerminalDao,
): TerminalLocalDataSource {

    override fun observeTerminalsByCommerceId(commerceId: String): Flow<List<Terminal>> =
        terminalDao
            .observeTerminalsByCommerceId(commerceId = commerceId)
            .map { entities -> entities.map { it.toDomain() } }
            .catch { emit(value = emptyList()) }

    override suspend fun isEmpty(commerceId: String): Result<Boolean> = runLocalCatching {
        !terminalDao.hasTerminals(commerceId = commerceId)
    }

    override suspend fun replaceTerminalsByCommerce(
        commerceId: String,
        terminals: List<Terminal>,
    ): Result<Unit> =
        runLocalCatching {
            terminalDao.replaceTerminalsByCommerce(
                commerceId = commerceId,
                terminals = terminals.map { it.toEntity() },
            )
        }

    override suspend fun deleteAllTerminals(): Result<Unit> =
        runLocalCatching {
            terminalDao.deleteAllTerminals()
        }
}