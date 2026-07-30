package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Terminal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ObserveTerminalsByCommerceIdUseCase {

    operator fun invoke(commerceId: String): Flow<List<Terminal>> = flowOf(value = emptyList())
}