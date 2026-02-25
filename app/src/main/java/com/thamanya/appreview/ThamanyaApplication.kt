package com.thamanya.appreview

import android.app.Application
import com.thamanya.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ThamanyaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@ThamanyaApplication)
            modules(modules)
        }
    }
}