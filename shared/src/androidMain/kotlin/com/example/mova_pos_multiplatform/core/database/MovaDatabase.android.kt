package com.example.mova_pos_multiplatform.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent.getKoin

actual fun getDatabaseBuilder(): RoomDatabase.Builder<MovaDatabase> {
    val context: Context = getKoin().get()
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(MovaDatabase.DB_NAME)

    return Room.databaseBuilder<MovaDatabase>(context = appContext, name = dbFile.absolutePath)
}
