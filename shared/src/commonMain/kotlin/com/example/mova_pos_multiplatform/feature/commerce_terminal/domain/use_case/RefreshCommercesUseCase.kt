package com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.CommerceRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository

class RefreshCommercesUseCase(
    private val commerceRepository: CommerceRepository,
    private val terminalRepository: TerminalRepository,
) {

    suspend operator fun invoke(forceRefresh: Boolean): Result<Unit> {
        return commerceRepository.refreshCommerces(forceRefresh = forceRefresh).onSuccess {
            if (forceRefresh) {
                terminalRepository.deleteAllTerminals()
            }
        }
    }
}