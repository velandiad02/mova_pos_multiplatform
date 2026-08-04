package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.transactions.data.hardware.FakeNfcReaderImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.hardware.FakeQrScannerImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.hardware.FakeReceiptPrinterImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.hardware.FakeTerminalInfoImpl
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.NfcReader
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.QrScanner
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.ReceiptPrinter
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.hardware.TerminalInfo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val hardwareModule = module {

    singleOf(constructor = ::FakeNfcReaderImpl) bind NfcReader::class
    singleOf(constructor = ::FakeQrScannerImpl) bind QrScanner::class
    singleOf(constructor = ::FakeReceiptPrinterImpl) bind ReceiptPrinter::class
    singleOf(constructor = ::FakeTerminalInfoImpl) bind TerminalInfo::class
}