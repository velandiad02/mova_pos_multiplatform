package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.ReceiptPrinter
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PrinterResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Receipt

class PrintReceptUseCase(
    private val receiptPrinter: ReceiptPrinter,
) {

    suspend operator fun invoke(receipt: Receipt): PrinterResult =
        receiptPrinter.print(receipt = receipt)
}