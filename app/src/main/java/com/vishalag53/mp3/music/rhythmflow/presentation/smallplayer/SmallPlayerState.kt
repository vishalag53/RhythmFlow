package com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer

sealed class SmallPlayerState {
    object Initial : SmallPlayerState()
    object Ready : SmallPlayerState()
}