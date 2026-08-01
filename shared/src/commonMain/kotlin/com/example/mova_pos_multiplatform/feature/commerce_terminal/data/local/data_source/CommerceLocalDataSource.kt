package com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow

interface CommerceLocalDataSource {

    fun observeCommerces(): Flow<List<Commerce>>

    suspend fun isEmpty(): Result<Boolean>

    suspend fun replaceAllCommerces(commerces: List<Commerce>): Result<Unit>
}