package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.CommerceRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow

class ObserveCommercesUseCase(
    private val repository: CommerceRepository,
) {

    operator fun invoke(): Flow<List<Commerce>> = repository.observeCommerces()
}