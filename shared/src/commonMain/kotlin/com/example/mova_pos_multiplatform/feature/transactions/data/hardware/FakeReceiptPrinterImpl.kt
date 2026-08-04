package com.example.mova_pos_multiplatform.feature.transactions.data.hardware

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.ReceiptPrinter
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PrinterResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Receipt
import kotlinx.coroutines.delay

class FakeReceiptPrinterImpl : ReceiptPrinter {

    override suspend fun print(receipt: Receipt): PrinterResult {
        delay(timeMillis = 2000L)

        return when (receipt.terminalName) {
            "POS-CAJA-RAPIDA" -> PrinterResult.OutOfPaper
            "POS-AUTOSERVICIO" -> PrinterResult.PrinterNotConnected
            else -> PrinterResult.Success
        }
    }
}