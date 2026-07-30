package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

class RefreshTerminalsByCommerceIdUseCase {

    operator fun invoke(commerceId: String): Result<Unit> = Result.success(value = Unit)
}