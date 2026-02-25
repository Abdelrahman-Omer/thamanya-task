package com.thamanya.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thamanya.home.presentation.HomeScreenRoot
import com.thamanya.presentation.components.custom_toast.ToastState
import com.thamanya.presentation.navigation.Routes

fun NavGraphBuilder.homeScreen(toastState: ToastState) {
    composable(Routes.HOME) {
        HomeScreenRoot(toastState = toastState)
    }
}
