package com.example.mova_pos_multiplatform

import android.app.Application
import com.example.mova_pos_multiplatform.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MovaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MovaApplication)
        }
    }
}