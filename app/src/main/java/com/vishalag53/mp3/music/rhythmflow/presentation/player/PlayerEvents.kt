package com.vishalag53.mp3.music.rhythmflow.presentation.player

sealed class PlayerEvents {
    object PlayPause : PlayerEvents()
    object SeekToNextItem : PlayerEvents()
    object SeekToPreviousItem : PlayerEvents()
    object Forward : PlayerEvents()
    object Backward : PlayerEvents()
    data class SetShuffle(val shuffleModeEnabled: Boolean) : PlayerEvents()
    data class SetVolume(val volume: Float): PlayerEvents()
    data class SetRepeatMode(val repeatMode: Int) : PlayerEvents()
    data class PlayBackSpeed(val playbackSpeed: Float) : PlayerEvents()
    data class UpdateProgress(val newProgress: Float) : PlayerEvents()
    data class SelectedAudioChange(val index: Int) : PlayerEvents()
}