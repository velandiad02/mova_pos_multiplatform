package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ObserveTerminalsByCommerceIdUseCase(
    private val repository: TerminalRepository,
) {

    operator fun invoke(commerceId: String?): Flow<List<Terminal>> {
        if (commerceId.isNullOrEmpty()) {
            return flowOf(value = emptyList())
        }

        return repository.observeTerminalsByCommerceId(commerceId = commerceId)
    }
}