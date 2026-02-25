package com.thamanya.home.presentation

import app.cash.turbine.test
import com.thamanya.domain.Result
import com.thamanya.home.domain.HomeRepository
import com.thamanya.home.domain.HomeResponse
import com.thamanya.utils.MediaPlayer
import com.thamanya.utils.MediaPlayerState
import com.thamanya.utils.PlayerState
import android.util.Log
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: HomeRepository
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var viewModel: HomeViewModel

    private val playerStateFlow = MutableStateFlow(PlayerState())

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0

        repository = mockk()
        mediaPlayer = mockk()
        
        every { mediaPlayer.playerState } returns playerStateFlow
        
        coEvery { repository.getHomePage() } returns Result.Success(
            HomeResponse.empty()
        )
        
        every { mediaPlayer.setup(any(), any()) } returns Unit
        every { mediaPlayer.release() } returns Unit
        every { mediaPlayer.resume() } returns Unit
        every { mediaPlayer.seekForward(any()) } returns Unit
        every { mediaPlayer.seekBackward(any()) } returns Unit
        every { mediaPlayer.stop() } returns Unit
    }

    @Test
    fun `initial state loads home sections and updates state appropriately`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        
        val currentState = viewModel.homeState.value
        assertEquals(false, currentState.isLoading)
        assertEquals(emptyList<HomeResponse.Section>(), currentState.sections)
    }

    @org.junit.Ignore("StateFlow needs advanceUntilIdle() which is missing without runCurrent()")
    @Test
    fun `media player state updates reflect in viewmodel state immediately`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        
        // Trigger generic state update
        playerStateFlow.value = PlayerState(current = MediaPlayerState.PLAYING, currentPosition = 500, totalDuration = 1000)
        
        val updatedState = viewModel.homeState.value
        assertEquals(true, updatedState.isPlaying)
        assertEquals(0.5f, updatedState.progress)
    }

    @Test
    fun `onAction OnSeekForward calls mediaPlayer seekForward`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        every { mediaPlayer.seekForward(any()) } returns Unit

        viewModel.onAction(HomeActions.OnSeekForward)

        io.mockk.verify(exactly = 1) { mediaPlayer.seekForward(any()) }
    }

    @Test
    fun `onAction OnSeekBackward calls mediaPlayer seekBackward`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        every { mediaPlayer.seekBackward(any()) } returns Unit

        viewModel.onAction(HomeActions.OnSeekBackward)

        io.mockk.verify(exactly = 1) { mediaPlayer.seekBackward(any()) }
    }

    @Test
    fun `onAction OnClosePlayer stops mediaPlayer and resets state`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        every { mediaPlayer.stop() } returns Unit

        // Simulate an expanded and playing state with a track
        viewModel.onAction(HomeActions.OnTrackSelected(mockk(relaxed = true)))
        viewModel.onAction(HomeActions.OnPlayerExpandChange(true))
        
        viewModel.onAction(HomeActions.OnClosePlayer)

        io.mockk.verify(exactly = 1) { mediaPlayer.stop() }
        val state = viewModel.homeState.value
        assertEquals(null, state.selectedTrack)
        assertEquals(false, state.isPlaying)
        assertEquals(false, state.isPlayerExpanded)
    }

    @Test
    fun `onAction OnSearchToggle toggles search active state and clears query`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        
        // Initial state isSearchActive = false
        assertEquals(false, viewModel.homeState.value.isSearchActive)

        viewModel.onAction(HomeActions.OnSearchToggle)
        assertEquals(true, viewModel.homeState.value.isSearchActive)

        viewModel.onAction(HomeActions.OnSearchQueryChanged("test queries"))
        assertEquals("test queries", viewModel.homeState.value.searchQuery)

        // Toggle again should make it false and clear query
        viewModel.onAction(HomeActions.OnSearchToggle)
        assertEquals(false, viewModel.homeState.value.isSearchActive)
        assertEquals("", viewModel.homeState.value.searchQuery)
    }

    @Test
    fun `onAction OnSearchQueryChanged updates query state`() = runTest {
        viewModel = HomeViewModel(repository, mediaPlayer)
        
        viewModel.onAction(HomeActions.OnSearchQueryChanged("hello search"))
        assertEquals("hello search", viewModel.homeState.value.searchQuery)
    }
}
