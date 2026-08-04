package com.example.mova_pos_multiplatform.feature.transactions.domain.use_case

import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.NfcReader
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.QrScanner
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.service.LinkReader
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.ChannelResult
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.Money
import com.example.mova_pos_multiplatform.feature.transactions.domain.model.PaymentChannel

class ReadTransactionChannelUseCase(
    private val qrScanner: QrScanner,
    private val nfcReader: NfcReader,
    private val linkReader: LinkReader,
) {

    suspend operator fun invoke(channel: PaymentChannel, amount: Money): ChannelResult {
        return when (channel) {
            PaymentChannel.QR -> qrScanner.generateAndRead(amount = amount)
            PaymentChannel.NFC -> nfcReader.read(amount = amount)
            PaymentChannel.PAYMENT_LINK -> linkReader.generateAndRead(amount = amount)
        }
    }
}