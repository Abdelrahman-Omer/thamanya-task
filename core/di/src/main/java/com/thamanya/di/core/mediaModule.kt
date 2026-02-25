package com.thamanya.di.core

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thamanya.data.HttpClientFactory
import com.thamanya.data.dataStore.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
val mediaModule = module {
    single { MediaPlayer(androidApplication()) }
}