package com.testtask

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class ThisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ThisApplication)
            modules(
                listOf(
                    uiModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}