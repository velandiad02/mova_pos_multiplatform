package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source

import com.example.mova_pos_multiplatform.core.common.utils.runLocalCatching
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.CommerceDao
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper.toDomain
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.mapper.toEntity
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CommerceLocalDataSourceImpl(
    private val commerceDao: CommerceDao,
) : CommerceLocalDataSource {

    override fun observeCommerces(): Flow<List<Commerce>> =
        commerceDao
            .observeCommerces()
            .map { entities -> entities.map { it.toDomain() } }
            .catch { emit(value = emptyList()) }

    override suspend fun getCommerceById(id: String): Result<Commerce> = runLocalCatching {
        commerceDao.getCommerceById(id = id).toDomain()
    }

    override suspend fun isEmpty(): Result<Boolean> = runLocalCatching {
        !commerceDao.hasCommerces()
    }

    override suspend fun replaceAllCommerces(commerces: List<Commerce>): Result<Unit> =
        runLocalCatching {
            commerceDao.replaceAll(commerces = commerces.map { it.toEntity() })
        }
}