package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

class GetTransactionsByTerminalUseCase(
    private val repository: TransactionRepository,
) {

    suspend operator fun invoke(terminalId: String? = null): Result<List<Transaction>> =
        repository.getTransactionsByTerminalId(terminalId = terminalId)
}
