package com.thamanya.presentation.components.custom_toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ToastHost(
    toastState: ToastState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter // Changed to TopCenter
    ) {
        AnimatedVisibility(
            visible = toastState.isVisible,
            enter = fadeIn(animationSpec = tween(300)) +
                    slideInVertically(animationSpec = tween(300)) { -it }, // Changed to -it to slide from top
            exit = fadeOut(animationSpec = tween(300)) +
                    slideOutVertically(animationSpec = tween(300)) { -it } // Changed to -it to slide to top
        ) {
            val toastData = toastState.toastData

            LaunchedEffect(toastState.isVisible) {
                if (toastState.isVisible) {
                    delay(toastData.durationMillis)
                    toastState.hideToast()
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 48.dp, start = 8.dp, end = 8.dp)
                    .heightIn(76.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(toastData.backgroundColor)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = toastData.message,
                    color = toastData.textColor,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Remember Toast State
@Composable
fun rememberToastState(): ToastState {
    return remember { ToastState() }
}

@Composable
fun WithToast(
    content: @Composable (ToastState) -> Unit
) {
    val toastState = rememberToastState()

    Box(modifier = Modifier.fillMaxSize()) {
        content(toastState)
        ToastHost(toastState = toastState)
    }
}

val LocalToastState = staticCompositionLocalOf<ToastState> {
    error("LocalToastState is not provided")
}