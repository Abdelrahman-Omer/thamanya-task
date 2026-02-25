@file:OptIn(FlowPreview::class)

package com.thamanya.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thamanya.domain.onError
import com.thamanya.domain.onSuccess
import com.thamanya.home.domain.HomeRepository
import com.thamanya.home.domain.HomeResponse
import com.thamanya.home.domain.model.Chip
import com.thamanya.presentation.R
import com.thamanya.presentation.domain.AudioTrack
import com.thamanya.presentation.toUiText
import com.thamanya.presentation.uiText
import com.thamanya.utils.MediaPlayer
import com.thamanya.utils.MediaPlayerState
import com.thamanya.utils.replace
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val mediaPlayer: MediaPlayer
) : ViewModel() {

    private var cachedSection = emptyList<HomeResponse.Section>()
    private var searchJob: Job? = null

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        observeSearchQuery()
        getHomeSections()
        viewModelScope.launch {
            mediaPlayer.playerState.collect { playerState ->
                val progress = if (playerState.totalDuration > 0) {
                    (playerState.currentPosition.toFloat() / playerState.totalDuration.toFloat()).coerceIn(0f, 1f)
                } else {
                    0f
                }

                val isPlaying = when(playerState.current) {
                    MediaPlayerState.PLAYING -> true
                    MediaPlayerState.PAUSED, MediaPlayerState.ENDED -> false
                    else -> false // or keep current state
                }
                _homeState.update {
                    it.copy(
                        isPlaying = isPlaying,
                        progress = progress
                    )
                }
            }
        }
    }
    fun onAction(action: HomeActions) {
        when (action) {
            is HomeActions.OnCategorySelected -> onCategorySelected(action.onCategorySelected)
            is HomeActions.OnPlayerExpandChange -> onPlayerExpandChange(action.isExpanded)
            is HomeActions.OnTrackPlaying -> onTrackPlaying(action.isPlaying)
            is HomeActions.OnTrackSelected -> onTrackSelected(action.track)
            is HomeActions.OnSeekForward -> seekForward()
            is HomeActions.OnSeekBackward -> seekBackward()
            is HomeActions.OnClosePlayer -> onClosePlayer()
            is HomeActions.OnSearchToggle -> onSearchToggle()
            is HomeActions.OnSearchQueryChanged -> onSearchQueryChanged(action.query)
            is HomeActions.OnClearError -> onClearError()
            is HomeActions.OnProgressChange -> onProgressChange(action.progress)
        }
    }

    private fun onProgressChange(progress: Float) {
        val totalDuration = mediaPlayer.playerState.value.totalDuration
        val newPosition = (progress * totalDuration).toLong()
        mediaPlayer.seekTo(newPosition)
    }

    private fun seekForward() {
        mediaPlayer.seekForward()
    }

    private fun seekBackward() {
        mediaPlayer.seekBackward()
    }

    private fun onClosePlayer() {
        mediaPlayer.stop()
        _homeState.update {
            it.copy(
                selectedTrack = null,
                isPlaying = false,
                isPlayerExpanded = false
            )
        }
    }

    private fun onSearchToggle() {
        _homeState.update {
            it.copy(
                isSearchActive = !it.isSearchActive,
                searchQuery = "" // clear search query on toggle
            )
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _homeState.update {
            it.copy(searchQuery = query)
        }
    }

    private fun onClearError() {
        _homeState.update {
            it.copy(
                errorMessage = null
            )
        }
    }

    private fun onTrackSelected(selectedTrack: HomeResponse.Section.Content) {
        if (selectedTrack.audioUrl.isEmpty()){
            _homeState.update {
                it.copy(
                    errorMessage = uiText(R.string.selected_track_invalid)
                )
            }
            return
        }

        mediaPlayer.release()

        _homeState.update {
            it.copy(
                selectedTrack = AudioTrack(
                    title = selectedTrack.name,
                    id = selectedTrack.podcastId,
                    audioUrl = selectedTrack.audioUrl,
                    artist = selectedTrack.authorName,
                    albumArt = selectedTrack.avatarUrl,
                    duration = selectedTrack.duration.toFloat()
                )
            )
        }

        mediaPlayer.setup(url = selectedTrack.audioUrl)
    }

    private fun onTrackPlaying(isPlaying: Boolean) {
        if (isPlaying){
            resumeMedia()
        } else {
            pauseMedia()
        }

        _homeState.update {
            it.copy(
                isPlaying = isPlaying
            )
        }
    }

    private fun onPlayerExpandChange(isExpanded: Boolean) {
        _homeState.update {
            it.copy(
                isPlayerExpanded = isExpanded
            )
        }
    }

    private fun onCategorySelected(selectedCategory: Chip) {
        val newList = homeState.value.categories.onEach { it.isSelected = false }
        _homeState.update {
            it.copy(
                categories = newList.replace(selectedCategory){ chip ->
                    chip.copy(
                        isSelected = true
                    )
                }
            )
        }
    }

    private fun observeSearchQuery() {
        homeState
            .map { state ->
                // If search is active and query is not empty, use that.
                // Otherwise fallback to the selected category chip text.
                if (state.isSearchActive && state.searchQuery.isNotBlank()) {
                    state.searchQuery
                } else {
                    state.selectedOption?.text ?: ""
                }
            }
            .distinctUntilChanged()
            .debounce(400L)
            .onEach { query ->
                when {
                    query.equals("الجميع") -> {
                        searchJob?.cancel()
                        getHomeSections()
                    }

                    !query.equals("الجميع") -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getHomeSections() {
        updateLoadingStatus(isLoading = true)
        viewModelScope.launch {
            homeRepository
                .getHomePage()
                .onSuccess { data ->
                    _homeState.update {
                        it.copy(
                            isLoading = false,
                            sections = data.sections
                        )
                    }
                }
                .onError {
                    updateLoadingStatus(isLoading = false)
                }
        }
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        updateLoadingStatus(isLoading = true)

        homeRepository
            .searchHomePage(query)
            .onSuccess { searchResults ->
                _homeState.update {
                    it.copy(
                        isLoading = false,
                        sections = searchResults.sections
                    )
                }
            }
            .onError { error ->
                _homeState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun updateLoadingStatus(isLoading: Boolean){
        _homeState.update { it.copy(isLoading = isLoading) }
    }

    private fun resumeMedia() {
        mediaPlayer.resume()
    }

    private fun pauseMedia() {
        mediaPlayer.pause()
    }
}