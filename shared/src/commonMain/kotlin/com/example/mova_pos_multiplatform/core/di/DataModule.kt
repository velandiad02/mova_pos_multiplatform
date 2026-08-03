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
import com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source.TransactionLocalDataSource
import com.example.mova_pos_multiplatform.feature.transactions.data.local.data_source.TransactionLocalDataSourceImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.data_source.TransactionRemoteDatasource
import com.example.mova_pos_multiplatform.feature.transactions.data.remote.data_source.TransactionRemoteDatasourceImpl
import com.example.mova_pos_multiplatform.feature.transactions.data.repository.TransactionRepositoryImpl
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.repository.TransactionRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {

    singleOf(constructor = ::CommerceLocalDataSourceImpl) bind CommerceLocalDataSource::class
    singleOf(constructor = ::TerminalLocalDataSourceImpl) bind TerminalLocalDataSource::class
    singleOf(constructor = ::TransactionLocalDataSourceImpl) bind TransactionLocalDataSource::class

    singleOf(constructor = ::CommerceRemoteDataSourceImpl) bind CommerceRemoteDataSource::class
    singleOf(constructor = ::TerminalRemoteDataSourceImpl) bind TerminalRemoteDataSource::class
    singleOf(constructor = ::TransactionRemoteDatasourceImpl) bind TransactionRemoteDatasource::class

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

    single<TransactionRepository> {
        TransactionRepositoryImpl(
            localDataSource = get(),
            remoteDatasource = get(),
            ioDispatcher = get(qualifier = named(name = DispatchersNamed.IO)),
        )
    }
}