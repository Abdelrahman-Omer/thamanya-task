package com.thamanya.di.core

import com.thamanya.data.dataStore.AppDataStore
import com.thamanya.data.dataStore.PreferencesDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val localStorageModule = module {
    single<PreferencesDataStore> { AppDataStore(get()) }
}