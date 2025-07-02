package com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer

sealed class SmallPlayerEvents {
    object PlayPause : SmallPlayerEvents()
    object SeekToNextItem : SmallPlayerEvents()
    object SeekToPreviousItem : SmallPlayerEvents()
    object ClearMediaItems : SmallPlayerEvents()
    data class UpdateProgress(val newProgress: Float) : SmallPlayerEvents()
    data class SelectedAudioChange(val index: Int) : SmallPlayerEvents()
}