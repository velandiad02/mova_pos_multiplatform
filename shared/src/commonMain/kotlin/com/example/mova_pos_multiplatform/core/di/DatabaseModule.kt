package com.example.mova_pos_multiplatform.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.mova_pos_multiplatform.core.database.MovaDatabase
import com.example.mova_pos_multiplatform.core.database.getDatabaseBuilder
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.CommerceDao
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.TerminalDao
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {

    single<MovaDatabase> {
        getDatabaseBuilder()
            .fallbackToDestructiveMigration(dropAllTables = true)
            .setQueryCoroutineContext(context = get(qualifier = named(DispatchersNamed.IO)))
            .setDriver(driver = BundledSQLiteDriver())
            .build()
    }

    single<CommerceDao> {
        get<MovaDatabase>().getCommerceDao()
    }

    single<TerminalDao> {
        get<MovaDatabase>().getTerminalDao()
    }
}