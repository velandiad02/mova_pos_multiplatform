package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.core.common.extension.formatCurrency
import com.example.mova_pos_multiplatform.core.common.extension.formatDate
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.CommerceRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Receipt
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Transaction

class GenerateReceiptUseCase(
    private val commerceRepository: CommerceRepository,
    private val terminalRepository: TerminalRepository,
) {

    suspend operator fun invoke(transaction: Transaction): Result<Receipt> {
        val terminal = terminalRepository.getTerminalById(id = transaction.terminalId)
            .getOrElse { exception -> return Result.failure(exception) }

        val commerce = commerceRepository.getCommerceById(id = terminal.commerceId)
            .getOrElse { exception -> return Result.failure(exception) }

        val receipt = Receipt(
            idempotencyKey = transaction.idempotencyKey,
            commerceName = commerce.name,
            terminalName = terminal.name,
            amountFormatted = transaction.amount.formatCurrency(),
            dateFormatted = transaction.createdAt.formatDate(),
            channel = transaction.channel,
            status = transaction.status,
        )

        return Result.success(value = receipt)
    }
}