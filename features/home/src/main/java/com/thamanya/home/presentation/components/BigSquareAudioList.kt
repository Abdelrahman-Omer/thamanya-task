package com.thamanya.home.presentation.components

import androidx.compose.animation.animateContentSize

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thamanya.home.domain.HomeResponse
import com.thamanya.presentation.components.onClick
import com.thamanya.designsystem.theme.GrayBackgroundColor
import com.thamanya.utils.getTimeDescription

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BigSquareAudioList(
    modifier: Modifier = Modifier,
    section: HomeResponse.Section,
    onTrackSelected: (HomeResponse.Section.Content) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = (configuration.screenWidthDp).minus(64)

    LazyRow(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(section.content) {content ->
            Box(
                modifier = Modifier
                    .width(screenWidthDp.dp)
                    .height(IntrinsicSize.Min)
                    .animateContentSize()
                    .onClick { onTrackSelected(content) }
                    .background(GrayBackgroundColor, shape = RoundedCornerShape(16.dp))

            ) {
                AsyncImage(
                    content.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        content.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        getTimeDescription(content.releaseDate),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}