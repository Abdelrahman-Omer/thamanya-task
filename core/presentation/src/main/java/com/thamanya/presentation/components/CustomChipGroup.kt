package com.thamanya.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomChipGroup(
    options: List<String>,
    selectedOptions: List<String> = emptyList(),
    onSelectionChanged: (List<String>) -> Unit,
    multiSelect: Boolean = false,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { option ->
            val isSelected = selectedOptions.contains(option)

            AnimatedCustomChip(
                text = option,
                isSelected = isSelected,
                onClick = {
                    val newSelection = if (multiSelect) {
                        if (isSelected) {
                            selectedOptions - option
                        } else {
                            selectedOptions + option
                        }
                    } else {
                        if (isSelected) emptyList() else listOf(option)
                    }
                    onSelectionChanged(newSelection)
                }
            )
        }
    }
}