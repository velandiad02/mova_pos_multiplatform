package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.ObserveTerminalsByCommerceIdUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.RefreshCommercesUseCase
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.use_case.SyncTerminalsForCommerceUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    factoryOf(constructor = ::ObserveCommercesUseCase)
    factoryOf(constructor = ::ObserveTerminalsByCommerceIdUseCase)
    factoryOf(constructor = ::RefreshCommercesUseCase)
    factoryOf(constructor = ::SyncTerminalsForCommerceUseCase)
}