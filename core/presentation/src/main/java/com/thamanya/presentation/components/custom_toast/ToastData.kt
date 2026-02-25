package com.thamanya.presentation.components.custom_toast

import androidx.compose.ui.graphics.Color

data class ToastData(
    val message: String,
    val backgroundColor: Color = Color(0xDE000000),
    val textColor: Color = Color.White,
    val durationMillis: Long = 2000
)