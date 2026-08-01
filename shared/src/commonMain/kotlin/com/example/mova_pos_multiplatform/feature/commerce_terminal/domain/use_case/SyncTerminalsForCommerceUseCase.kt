package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository

class SyncTerminalsForCommerceUseCase(
    private val repository: TerminalRepository,
) {

    suspend operator fun invoke(commerceId: String): Result<Unit> =
        repository.syncTerminalsForCommerce(commerceId = commerceId)
}