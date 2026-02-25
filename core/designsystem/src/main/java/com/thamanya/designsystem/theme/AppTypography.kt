package com.thamanya.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thamanya.designsystem.R

@Composable
fun ibmFontFamily() = FontFamily(
    Font(R.font.ipsa_thin, weight = FontWeight.Light),
    Font(R.font.ipsa_regular, weight = FontWeight.Normal),
    Font(R.font.ipsa_medium, weight = FontWeight.Medium),
    Font(R.font.ipsa_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.ipsa_bold, weight = FontWeight.Bold)
)

@Composable
fun appTypography() = Typography().run {
    val fontFamily = ibmFontFamily()
    copy(
        displayLarge = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        displayMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        displaySmall = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        headlineLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        headlineMedium = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        headlineSmall = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        bodyMedium = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        bodySmall = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        titleMedium = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = TextColor,
            fontFamily = fontFamily
        ),
        titleSmall = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = TextColor,
            fontFamily = fontFamily
        )
    )
}
