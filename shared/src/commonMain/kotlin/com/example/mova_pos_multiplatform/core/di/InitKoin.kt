package com.example.mova_pos_multiplatform.core.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            databaseModule,
            networkModule,
            dispatcherModule,
            dataModule,
            domainModule,
            hardwareModule,
            serviceModule,
        )
    }
}