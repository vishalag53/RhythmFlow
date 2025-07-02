package com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.components

sealed class SmallPlayerEvents {
    object PlayPause : SmallPlayerEvents()
    data class SelectedAudioChange(val index: Int) : SmallPlayerEvents()
    data class SeekTo(val position: Float) : SmallPlayerEvents()
    object Backward : SmallPlayerEvents()
    object SeekToNextItem : SmallPlayerEvents()
    object SeekToPreviousItem : SmallPlayerEvents()
    object Forward : SmallPlayerEvents()
    data class UpdateProgress(val newProgress: Float) : SmallPlayerEvents()
}