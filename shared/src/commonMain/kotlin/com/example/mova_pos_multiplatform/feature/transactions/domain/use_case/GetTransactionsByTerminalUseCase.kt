package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

class GetTransactionsByTerminalUseCase(
    private val repository: TransactionRepository,
) {

    operator fun invoke(terminalId: String): Flow<List<Transaction>> =
        repository.observeTransactionsByTerminalId(terminalId = terminalId)
}
