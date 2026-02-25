package com.thamanya.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerListTextItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.3f)
                .height(20.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerListBigSquareItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.7f)
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        )
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerListSquareItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )

            Box(
                modifier = modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
        }
    } else {
        contentAfterLoading()
    }
}