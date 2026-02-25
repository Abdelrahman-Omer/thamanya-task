package com.thamanya.di

import android.annotation.SuppressLint
import com.thamanya.di.core.coreDataModule
import com.thamanya.di.core.localStorageModule
import com.thamanya.di.features.home.homeModule

@SuppressLint("NewApi")
val modules = listOf(
    localStorageModule,
    coreDataModule,
    homeModule,
)