package com.thamanya.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thamanya.home.domain.model.Chip
import com.thamanya.home.presentation.HomeState

@Composable
fun CustomChipGroup(
    modifier: Modifier = Modifier,
    onSelectionChanged: (Chip) -> Unit,
    options: List<Chip>
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { option ->

            AnimatedCustomChip(
                text = option.text,
                isSelected = option.isSelected,
                onClick = {
                    onSelectionChanged(option)
                }
            )
        }
    }
}

@Composable
fun HomeCategoryChips(
    modifier: Modifier = Modifier,
    state: HomeState,
    onSelectionChanged: (Chip) -> Unit
) {
    CustomChipGroup(
        modifier = modifier,
        options = state.categories,
        onSelectionChanged = onSelectionChanged
    )
}