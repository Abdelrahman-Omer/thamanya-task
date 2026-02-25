package com.thamanya.presentation.domain

data class AudioTrack(
    val id: String,
    val title: String,
    val audioUrl: String,
    val artist: String,
    val albumArt: String?,
    val duration: Float // in seconds
)