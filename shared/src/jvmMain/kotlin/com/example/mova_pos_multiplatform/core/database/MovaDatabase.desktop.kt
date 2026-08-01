package com.example.mova_pos_multiplatform.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MovaDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), MovaDatabase.DB_NAME)

    return Room.databaseBuilder<MovaDatabase>(name = dbFile.absolutePath)
}