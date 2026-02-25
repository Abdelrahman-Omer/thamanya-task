package com.thamanya.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thamanya.presentation.R

@Composable
fun UserAndNotificationHeader(
    modifier: Modifier = Modifier,
    userName:String = "عبدالرحمن",
    missedNotification: String = "4",
    isSearchActive: Boolean = false,
    searchQuery: String = "",
    onSearchToggle: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isSearchActive) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                placeholder = { Text("بحث") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = onSearchToggle) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close Search")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = com.thamanya.designsystem.theme.PrimaryOrange,
                    unfocusedBorderColor = Color.LightGray
                )
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_user),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF54B974),
                        shape = CircleShape
                    ),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Text(
                stringResource(R.string.good_evening, userName),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onSearchToggle) {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Box {
            Image(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )

            Text(
                missedNotification,
                fontSize = 10.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .size(14.dp)
                    .align(Alignment.TopEnd)
                    .background(color = Color.Red, shape = CircleShape)
                    .clip(CircleShape)
            )
        }
    }
}