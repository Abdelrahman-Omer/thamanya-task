package com.thamanya.appreview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.thamanya.designsystem.theme.ThamanyaTheme
import com.thamanya.home.presentation.navigation.homeScreen
import com.thamanya.presentation.components.custom_toast.WithToast
import com.thamanya.presentation.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThamanyaTheme {
                WithToast { toastState ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME
                    ) {
                        homeScreen(toastState = toastState)
                    }
                }
            }
        }
    }
}