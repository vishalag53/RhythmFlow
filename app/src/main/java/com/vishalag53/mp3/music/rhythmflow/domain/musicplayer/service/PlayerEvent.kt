package com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object SelectedAudioChange : PlayerEvent()
    object Backward : PlayerEvent()
    object SeekToNextItem : PlayerEvent()
    object SeekToPreviousItem : PlayerEvent()
    object SeekTo : PlayerEvent()
    object Forward : PlayerEvent()
    object Stop : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
    data class PlayBackSpeed(val playbackSpeed: Float) : PlayerEvent()
}