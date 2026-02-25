package com.thamanya.di.features.home

import android.annotation.SuppressLint
import com.thamanya.utils.MediaPlayer
import com.thamanya.home.data.network.KtorRemoteHomeDataSource
import com.thamanya.home.data.network.RemoteHomeDataSource
import com.thamanya.home.data.repository.HomeRepositoryImpl
import com.thamanya.home.domain.HomeRepository
import com.thamanya.home.presentation.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@SuppressLint("NewApi")
val homeModule = module {
    single<RemoteHomeDataSource> { KtorRemoteHomeDataSource(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    single { MediaPlayer(androidApplication()) }
    viewModelOf(::HomeViewModel)
}