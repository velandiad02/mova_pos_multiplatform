package com.example.mova_pos_multiplatform.core.di

import com.example.mova_pos_multiplatform.feature.transactions.data.service.FakeLinkReaderImpl
import com.example.mova_pos_multiplatform.feature.transactions.domain.boundary.service.LinkReader
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule = module {

    factoryOf(constructor = ::FakeLinkReaderImpl) bind LinkReader::class
}