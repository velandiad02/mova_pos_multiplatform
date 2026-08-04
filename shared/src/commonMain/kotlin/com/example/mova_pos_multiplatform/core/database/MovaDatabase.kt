package com.example.mova_pos_multiplatform.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.CommerceDao
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.dao.TerminalDao
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.CommerceEntity
import com.example.mova_pos_multiplatform.feature.commerce_terminal.data.local.entity.TerminalEntity
import com.example.mova_pos_multiplatform.feature.transactions.data.local.dao.TransactionDao
import com.example.mova_pos_multiplatform.feature.transactions.data.local.entity.TransactionEntity

@Database(
    entities = [
        CommerceEntity::class,
        TerminalEntity::class,
        TransactionEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@ConstructedBy(MovaDatabaseConstructor::class)
abstract class MovaDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME: String = "mova.db"
    }

    abstract fun getCommerceDao(): CommerceDao
    abstract fun getTerminalDao(): TerminalDao
    abstract fun getTransactionDao(): TransactionDao
}

@Suppress("KotlinNoActualForExpect")
expect object MovaDatabaseConstructor : RoomDatabaseConstructor<MovaDatabase> {
    override fun initialize(): MovaDatabase
}

expect fun getDatabaseBuilder(): RoomDatabase.Builder<MovaDatabase>