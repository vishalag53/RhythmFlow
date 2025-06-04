package com.vishalag53.mp3.music.rhythmflow.musicplayer

sealed class UIState {
    object Initial: UIState()
    object Ready: UIState()
}