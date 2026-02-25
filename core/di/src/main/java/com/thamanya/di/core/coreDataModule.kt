package com.thamanya.di.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thamanya.data.HttpClientFactory
import com.thamanya.data.dataStore.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDataModule = module {
    single<DataStore<Preferences>> { createDataStore(androidContext()) }

    single { HttpClientFactory.create(get(), get()) }
    single<HttpClientEngine> {
        OkHttp.create {
            addInterceptor(com.thamanya.data.network.NetworkStatusInterceptor())
        }
    }
}