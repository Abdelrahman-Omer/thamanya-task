package com.thamanya.presentation.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CollapsibleAudioPlayerBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAudioPlayerButtons() {
        var playClicked = false
        var forwardClicked = false
        var backwardClicked = false

        composeTestRule.setContent {
            FullScreenCollapsibleAudioPlayer(
                isExpanded = true,
                onToggleExpanded = {},
                currentTrack = null,
                isPlaying = false,
                onPlayPause = { playClicked = true },
                progress = 0f,
                onProgressChange = {},
                onSeekForward = { forwardClicked = true },
                onSeekBackward = { backwardClicked = true },
                onClose = {  }
            )
        }

        // Click forward
        composeTestRule.onNodeWithContentDescription("Seek forward 15 seconds").performClick()
        assert(forwardClicked)

        // Click backward
        composeTestRule.onNodeWithContentDescription("Seek backward 15 seconds").performClick()
        assert(backwardClicked)

        // Click Play
        composeTestRule.onNodeWithContentDescription("Play").performClick()
        assert(playClicked)
    }
}
