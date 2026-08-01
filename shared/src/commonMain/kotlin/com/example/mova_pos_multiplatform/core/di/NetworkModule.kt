package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.core.network.getMockEngine
import com.example.mova_pos_multiplatform.core.network.provideHttpClient
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api.CommerceApi
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.api.TerminalApi
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> { provideHttpClient(mockEngine = getMockEngine()) }

    singleOf(constructor = ::CommerceApi)
    singleOf(constructor = ::TerminalApi)
}