package com.thamanya.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

enum class MediaPlayerState {
    IDLE, PLAYING, PAUSED, ENDED
}

data class PlayerState(
    val current: MediaPlayerState = MediaPlayerState.IDLE,
    val totalDuration: Long = 0L,
    val currentPosition: Long = 0L
)

class MediaPlayer(
    private val context: Context
) {
    private var listener: Player.Listener? = null
    private var mediaPlayer: ExoPlayer? = null
    private var positionHandler: Handler? = null
    private var positionRunnable: Runnable? = null

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState = _playerState.asStateFlow()

    val totalDuration: Long
        get() = mediaPlayer?.duration?.coerceAtLeast(0) ?: 0

    val currentPosition: Long
        get() = mediaPlayer?.currentPosition?.coerceAtLeast(0) ?: 0

    fun setup(
        url: String,
        playWhenReady: Boolean = true,
        speed: Float = 1f,
    ) {
        initialize(
            playWhenReady = playWhenReady,
            speed = speed
        ) {
            val mediaItem = MediaItem.fromUri(url)
            addMediaItem(mediaItem)
        }
    }

    private fun initialize(
        playWhenReady: Boolean = true,
        speed: Float = 1f,
        setup: ExoPlayer.() -> Unit
    ) {
        listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                updatePlayerState()
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                updatePlayerState()

                // Start/stop position updates based on playing state
                if (isPlaying) {
                    startPositionUpdates()
                } else {
                    stopPositionUpdates()
                }
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                updatePlayerState()
            }

            private fun updatePlayerState() {
                mediaPlayer?.let { player ->
                    val newState = player.playbackState.toPlayerState(player.isPlaying)
                    val duration = if (player.duration == -1L) 0L else player.duration.coerceAtLeast(0L)
                    val position = player.currentPosition.coerceAtLeast(0L)

                    _playerState.update {
                        it.copy(
                            current = newState,
                            totalDuration = duration,
                            currentPosition = position
                        )
                    }
                }
            }
        }

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }

        mediaPlayer = ExoPlayer.Builder(context)
            .build()
            .apply {
                listener?.let { addListener(it) }
                addAnalyticsListener(EventLogger())
                setup()
                setPlaybackSpeed(speed)
                prepare()
                setPlayWhenReady(playWhenReady)
            }

        setupPositionHandler()

        // Force start position updates if playing
        if (playWhenReady) {
            // Delay slightly to ensure player is ready
            positionHandler?.postDelayed({
                if (mediaPlayer?.isPlaying == true) {
                    startPositionUpdates()
                }
            }, 500)
        }

    }

    private fun setupPositionHandler() {
        positionHandler = Handler(Looper.getMainLooper())
        positionRunnable = object : Runnable {
            override fun run() {
                mediaPlayer?.let { player ->
                    val currentPos = player.currentPosition.coerceAtLeast(0L)
                    val isActuallyPlaying = player.isPlaying && player.playbackState == Player.STATE_READY

                    _playerState.update { state ->
                        state.copy(currentPosition = currentPos)
                    }

                    // Continue updates only if actually playing
                    if (isActuallyPlaying) {
                        positionHandler?.postDelayed(this, 100) // Update every 100ms
                    } else {
                    }
                }
            }
        }
    }

    private fun startPositionUpdates() {
        stopPositionUpdates() // Ensure we don't have multiple runnables
        positionRunnable?.let {
            positionHandler?.post(it)
        }
    }

    private fun stopPositionUpdates() {
        positionRunnable?.let { positionHandler?.removeCallbacks(it) }
    }

    fun resume() {
        mediaPlayer?.play()
    }

    fun stop() {
        mediaPlayer?.stop()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun seekTo(position: Long) {
        val safePosition = position.coerceIn(0, totalDuration)
        mediaPlayer?.seekTo(safePosition)
        // Immediately update the position in state
        _playerState.update { it.copy(currentPosition = safePosition) }
    }

    fun seekForward(ms: Long = 15000L) {
        seekTo(currentPosition + ms)
    }

    fun seekBackward(ms: Long = 15000L) {
        seekTo(currentPosition - ms)
    }

    fun setPlaybackSpeed(speed: Float) {
        mediaPlayer?.setPlaybackSpeed(speed)
    }

    fun release() {
        stopPositionUpdates()
        positionHandler = null
        positionRunnable = null

        mediaPlayer?.apply {
            listener?.let { removeListener(it) }
            this.release()
        }
        mediaPlayer = null
        listener = null
    }

    private fun Int.toPlayerState(isPlaying: Boolean): MediaPlayerState = when (this) {
        Player.STATE_IDLE -> MediaPlayerState.IDLE
        Player.STATE_ENDED -> MediaPlayerState.ENDED
        else -> if (isPlaying) MediaPlayerState.PLAYING else MediaPlayerState.PAUSED
    }

}