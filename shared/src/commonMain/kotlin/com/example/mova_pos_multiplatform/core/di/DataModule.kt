package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.CommerceLocalDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.CommerceLocalDataSourceImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.TerminalLocalDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.data_source.TerminalLocalDataSourceImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.CommerceRemoteDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.CommerceRemoteDataSourceImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.TerminalRemoteDataSource
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.remote.data_source.TerminalRemoteDataSourceImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.repository.CommerceRepositoryImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.repository.TerminalRepositoryImpl
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.CommerceRepository
import com.example.mova_pos_multiplatform.feature.commerce_terminal.domain.boundary.TerminalRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

    singleOf(constructor = ::CommerceLocalDataSourceImpl) bind CommerceLocalDataSource::class
    singleOf(constructor = ::TerminalLocalDataSourceImpl) bind TerminalLocalDataSource::class

    singleOf(constructor = ::CommerceRemoteDataSourceImpl) bind CommerceRemoteDataSource::class
    singleOf(constructor = ::TerminalRemoteDataSourceImpl) bind TerminalRemoteDataSource::class

    single<CommerceRepository> {
        CommerceRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            ioDispatcher = get(qualifier = named(name = DispatchersNamed.IO)),
        )
    }

    single<TerminalRepository> {
        TerminalRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            ioDispatcher = get(qualifier = named(name = DispatchersNamed.IO)),
        )
    }
}