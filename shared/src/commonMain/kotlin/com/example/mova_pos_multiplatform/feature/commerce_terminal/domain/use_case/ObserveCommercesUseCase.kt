package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.model.Commerce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ObserveCommercesUseCase {

    operator fun invoke(): Flow<List<Commerce>> = flowOf(value = emptyList())
}