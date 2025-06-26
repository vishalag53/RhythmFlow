package com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service

sealed class AudioEvent {
    object PlayPause: AudioEvent()
    object SelectAudioChange: AudioEvent()
    object Backward : AudioEvent()
    object SeekToNextItem : AudioEvent()
    object SeekToPreviousItem : AudioEvent()
    object SeekTo : AudioEvent()
    object Forward : AudioEvent()
    object Stop : AudioEvent()
    data class UpdateProgress(val newProgress: Float) : AudioEvent()
    data class PlayBackSpeed(val playbackSpeed: Float) : AudioEvent()
}