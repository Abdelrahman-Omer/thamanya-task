package com.thamanya.home.presentation

import com.thamanya.home.domain.HomeResponse
import com.thamanya.home.domain.model.Chip

sealed interface HomeActions {
    data class OnCategorySelected(val onCategorySelected: Chip): HomeActions
    data class OnPlayerExpandChange(val isExpanded: Boolean): HomeActions
    data class OnTrackPlaying(val isPlaying: Boolean): HomeActions
    data class OnProgressChange(val progress: Float): HomeActions
    data class OnTrackSelected(val track: HomeResponse.Section.Content): HomeActions
    data object OnSeekForward: HomeActions
    data object OnSeekBackward: HomeActions
    data object OnClosePlayer: HomeActions
    data object OnSearchToggle: HomeActions
    data class OnSearchQueryChanged(val query: String): HomeActions
    data object OnClearError: HomeActions
}