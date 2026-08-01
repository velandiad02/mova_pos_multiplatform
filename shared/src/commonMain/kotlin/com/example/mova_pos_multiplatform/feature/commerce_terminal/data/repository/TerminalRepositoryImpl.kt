package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.repository

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.TerminalLocalDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.TerminalRemoteDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TerminalRepositoryImpl(
    private val remoteDataSource: TerminalRemoteDataSource,
    private val localDataSource: TerminalLocalDataSource,
    private val ioDispatcher: CoroutineContext,
) : TerminalRepository {

    override fun observeTerminalsByCommerceId(commerceId: String): Flow<List<Terminal>> =
        localDataSource.observeTerminalsByCommerceId(commerceId = commerceId)

    override suspend fun syncTerminalsForCommerce(commerceId: String): Result<Unit> =
        withContext(context = ioDispatcher) {
            val isEmpty = localDataSource
                .isEmpty(commerceId = commerceId)
                .getOrDefault(defaultValue = false)

            if (isEmpty) {
                val result = remoteDataSource.fetchTerminalsByCommerceId(commerceId = commerceId)

                return@withContext result.fold(
                    onSuccess = {
                        localDataSource.replaceTerminalsByCommerce(
                            commerceId = commerceId,
                            terminals = it,
                        )
                    },
                    onFailure = {
                        Result.failure(exception = it)
                    }
                )
            }

            return@withContext Result.success(value = Unit)
        }

    override suspend fun deleteAllTerminals(): Result<Unit> = withContext(context = ioDispatcher) {
        localDataSource.deleteAllTerminals()
    }
}