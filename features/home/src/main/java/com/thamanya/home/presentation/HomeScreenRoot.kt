package com.thamanya.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thamanya.presentation.components.custom_toast.ToastState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    toastState: ToastState,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        HomeScreen(
            state = state,
            modifier = modifier,
            toastState = toastState,
            onAction = viewModel::onAction
        )
    }
}