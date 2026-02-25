package com.thamanya.presentation.components.custom_toast

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.thamanya.designsystem.theme.PrimaryDarkGreen
import com.thamanya.designsystem.theme.PrimaryLightGreen
import com.thamanya.designsystem.theme.PrimaryRed

class ToastState {
    private val _isVisible = mutableStateOf(false)
    private val _toastData = mutableStateOf(ToastData(""))
    
    val isVisible: Boolean get() = _isVisible.value
    val toastData: ToastData get() = _toastData.value
    
    fun showToast(toastData: ToastData) {
        _toastData.value = toastData
        _isVisible.value = true
    }

    fun showErrorToast(toastData: ToastData) {
        _toastData.value = toastData.copy(
            textColor = Color.White,
            backgroundColor = PrimaryRed
        )
        _isVisible.value = true
    }

    fun showSuccessToast(toastData: ToastData) {
        _toastData.value = toastData.copy(
            textColor = PrimaryDarkGreen,
            backgroundColor = PrimaryLightGreen
        )
        _isVisible.value = true
    }
    
    fun hideToast() {
        _isVisible.value = false
    }
}