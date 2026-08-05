package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveTerminalsByCommerceIdUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.RefreshCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.SyncTerminalsForCommerceUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.CreateTransactionUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GenerateReceiptUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.GetTransactionsByTerminalUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.PrintReceptUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.ReadTransactionChannelUseCase
import com.example.mova_pos_multiplatform.feature.transactions.domain.use_case.SyncTransactionsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    factoryOf(constructor = ::ObserveCommercesUseCase)
    factoryOf(constructor = ::ObserveTerminalsByCommerceIdUseCase)
    factoryOf(constructor = ::RefreshCommercesUseCase)
    factoryOf(constructor = ::SyncTerminalsForCommerceUseCase)

    factoryOf(constructor = ::CreateTransactionUseCase)
    factoryOf(constructor = ::GenerateReceiptUseCase)
    factoryOf(constructor = ::GetTransactionsByTerminalUseCase)
    factoryOf(constructor = ::PrintReceptUseCase)
    factoryOf(constructor = ::ReadTransactionChannelUseCase)
    factoryOf(constructor = ::SyncTransactionsUseCase)
}