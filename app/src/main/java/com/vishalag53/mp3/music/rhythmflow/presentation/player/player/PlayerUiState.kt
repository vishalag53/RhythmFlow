package com.vishalag53.mp3.music.rhythmflow.presentation.player.player

data class PlayerUiState(
    val isPlayingQueue: Boolean = false,
    val isSongInfo: Boolean = false,
    val isPlayingBack: Boolean = false,
    val isMenu: Boolean = false
)