package com.thamanya.home.presentation.components

import androidx.compose.animation.animateContentSize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thamanya.home.domain.HomeResponse
import com.thamanya.presentation.components.onClick

@Composable
fun SquareList(
    modifier: Modifier = Modifier,
    section: HomeResponse.Section,
    onTrackSelected: (HomeResponse.Section.Content) -> Unit
) {

    LazyRow(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(section.content) {content ->
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .animateContentSize()
                    .onClick { onTrackSelected(content) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    content.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Text(
                    content.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}