package com.testtask

import android.app.Application
import com.testtask.injection.dataModule
import com.testtask.injection.domainModule
import com.testtask.injection.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ThisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
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