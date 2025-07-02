package com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer

sealed class BasePlayerState {
    object Initial : BasePlayerState()
    object Ready : BasePlayerState()
}