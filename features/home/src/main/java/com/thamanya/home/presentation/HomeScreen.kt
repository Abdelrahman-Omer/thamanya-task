package com.thamanya.home.presentation

import com.thamanya.home.presentation.components.UserAndNotificationHeader
import com.thamanya.home.presentation.components.BigSquareAudioList
import com.thamanya.home.presentation.components.HomeCategoryChips
import com.thamanya.home.presentation.components.TwoLinesGridList
import com.thamanya.home.presentation.components.BigSquareList
import androidx.compose.foundation.layout.systemBarsPadding
import com.thamanya.home.presentation.components.SquareList
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.items
import com.thamanya.home.domain.SectionType
import androidx.compose.runtime.Composable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.thamanya.presentation.components.FullScreenCollapsibleAudioPlayer
import com.thamanya.presentation.components.ShimmerListBigSquareItem
import com.thamanya.presentation.components.ShimmerListSquareItem
import com.thamanya.presentation.components.ShimmerListTextItem
import com.thamanya.presentation.components.custom_toast.ToastData
import com.thamanya.presentation.components.custom_toast.ToastState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAction: (HomeActions) -> Unit,
    toastState: ToastState,
    state: HomeState
) {
    when{
        state.errorMessage != null -> {
            toastState.showErrorToast(
                ToastData(
                    message = state.errorMessage.asString(),
                    durationMillis = 3000
                )
            )
            onAction(HomeActions.OnClearError)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Main content
        Column(
            modifier = Modifier
                .padding(8.dp)
                .systemBarsPadding()
                .fillMaxSize()
        ) {

            UserAndNotificationHeader(
                isSearchActive = state.isSearchActive,
                searchQuery = state.searchQuery,
                onSearchToggle = { onAction(HomeActions.OnSearchToggle) },
                onSearchQueryChanged = { onAction(HomeActions.OnSearchQueryChanged(it)) }
            )

            HomeCategoryChips(
                modifier = Modifier.padding(top = 16.dp),
                state = state,
                onSelectionChanged = {
                    onAction(HomeActions.OnCategorySelected(it))
                }
            )

            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .padding(
                        bottom = when(state.selectedTrack == null){
                            true -> 0.dp
                            false -> 80.dp
                        }
                    )
                    .weight(1f)
                    .fillMaxSize()
            ) {
                if (state.isLoading) {
                    items(20) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            ShimmerListTextItem(
                                isLoading = state.isLoading,
                                contentAfterLoading = {},
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }

                        if (it % 2 == 0) {
                            ShimmerListSquareItem(
                                isLoading = state.isLoading,
                                contentAfterLoading = {},
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        } else {
                            ShimmerListBigSquareItem(
                                isLoading = state.isLoading,
                                contentAfterLoading = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                items(state.sections) { section ->
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            section.name,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    when (section.type) {
                        SectionType.BIG_SQUARE_AUDIO_BOOK -> BigSquareAudioList(
                            section = section,
                            onTrackSelected = { selectedContent ->
                                onAction(
                                    HomeActions.OnTrackSelected(
                                        track = selectedContent
                                    )
                                )
                            }
                        )
                        SectionType.TWO_LINES_GRID -> TwoLinesGridList(
                            section = section,
                            onTrackSelected = { selectedContent ->
                                onAction(
                                    HomeActions.OnTrackSelected(
                                        track = selectedContent
                                    )
                                )
                            }
                        )
                        SectionType.BIG_SQUARE -> BigSquareList(
                            section = section,
                            onTrackSelected = { selectedContent ->
                                onAction(
                                    HomeActions.OnTrackSelected(
                                        track = selectedContent
                                    )
                                )
                            }

                        )
                        SectionType.SQUARE -> SquareList(
                            section = section,
                            onTrackSelected = { selectedContent ->
                                onAction(
                                    HomeActions.OnTrackSelected(
                                        track = selectedContent
                                    )
                                )
                            }

                        )
                        SectionType.QUEUE -> SquareList(
                            section = section,
                            onTrackSelected = { selectedContent ->
                                onAction(
                                    HomeActions.OnTrackSelected(
                                        track = selectedContent
                                    )
                                )
                            }
                        )
                        else -> Unit
                    }
                }
            }

        }

        androidx.compose.animation.AnimatedVisibility(
            visible = state.selectedTrack != null,
            enter = androidx.compose.animation.slideInVertically(
                initialOffsetY = { it }
            ) + androidx.compose.animation.fadeIn(),
            exit = androidx.compose.animation.slideOutVertically(
                targetOffsetY = { it }
            ) + androidx.compose.animation.fadeOut()
        ) {
            FullScreenCollapsibleAudioPlayer(
                isExpanded = state.isPlayerExpanded,
                onToggleExpanded = {
                    onAction(
                        HomeActions.OnPlayerExpandChange(
                            isExpanded = !state.isPlayerExpanded
                        )
                    )
                },
                currentTrack = state.selectedTrack,
                isPlaying = state.isPlaying,
                onPlayPause = {
                    onAction(
                        HomeActions.OnTrackPlaying(
                            isPlaying = !state.isPlaying
                        )
                    )
                },
                progress = state.progress,
                onProgressChange = {
                    onAction(
                        HomeActions.OnProgressChange(
                            progress = it
                        )
                    )
                },
                onSeekForward = {
                    onAction(HomeActions.OnSeekForward)
                },
                onSeekBackward = {
                    onAction(HomeActions.OnSeekBackward)
                },
                onClose = {
                    onAction(HomeActions.OnClosePlayer)
                }
            )
        }
    }
}