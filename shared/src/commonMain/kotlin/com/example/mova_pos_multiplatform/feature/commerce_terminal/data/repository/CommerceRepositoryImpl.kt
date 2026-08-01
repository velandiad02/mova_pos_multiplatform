package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.repository

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.CommerceLocalDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.CommerceRemoteDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.CommerceRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CommerceRepositoryImpl(
    private val remoteDataSource: CommerceRemoteDataSource,
    private val localDataSource: CommerceLocalDataSource,
    private val ioDispatcher: CoroutineContext,
) : CommerceRepository {

    override fun observeCommerces(): Flow<List<Commerce>> = localDataSource.observeCommerces()

    override suspend fun refreshCommerces(forceRefresh: Boolean): Result<Unit> =
        withContext(context = ioDispatcher) {
            val isEmpty = localDataSource.isEmpty().getOrDefault(defaultValue = false)

            if (isEmpty || forceRefresh) {
                val result = remoteDataSource.fetchCommerces()

                return@withContext result.fold(
                    onSuccess = {
                        localDataSource.replaceAllCommerces(commerces = it)
                    },
                    onFailure = {
                        Result.failure(exception = it)
                    }
                )
            }

            return@withContext Result.success(value = Unit)
        }
}