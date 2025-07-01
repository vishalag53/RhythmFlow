package com.vishalag53.mp3.music.rhythmflow.presentation.player

sealed class PlayerState {
    object Initial : PlayerState()
    object Ready : PlayerState()
}