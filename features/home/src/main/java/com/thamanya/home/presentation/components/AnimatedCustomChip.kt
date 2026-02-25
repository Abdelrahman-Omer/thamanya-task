package com.thamanya.home.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamanya.presentation.components.onClick
import com.thamanya.designsystem.theme.PrimaryBlack
import com.thamanya.designsystem.theme.PrimaryOrange

@Composable
fun AnimatedCustomChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) {
            PrimaryOrange
        } else {
            PrimaryBlack
        },
        animationSpec = tween(300),
        label = "chip_color"
    )
    
    val animatedTextColor by animateColorAsState(
        targetValue = Color.White,
        animationSpec = tween(300),
        label = "text_color"
    )
    
    val animatedElevation by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )
    
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    Surface(
        modifier = modifier
            .scale(scale)
            .onClick { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(20.dp),
        color = animatedColor,
        shadowElevation = animatedElevation,
        border = BorderStroke(
            width = 1.dp,
            color = animatedColor
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = animatedTextColor,
            modifier = Modifier.padding(8.dp)
        )
    }
}