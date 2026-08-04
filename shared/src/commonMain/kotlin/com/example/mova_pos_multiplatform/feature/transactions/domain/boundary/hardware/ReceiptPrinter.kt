package com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware

import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PrinterResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Receipt

interface ReceiptPrinter {

    suspend fun print(receipt: Receipt): PrinterResult
}