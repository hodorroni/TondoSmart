package com.rony.tondo_smart

import android.app.Application
import com.rony.tondo_smart.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TondoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TondoApplication)
            modules(appModule)
        }
    }
}