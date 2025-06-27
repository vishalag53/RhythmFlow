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
    object ClearMediaItems : AudioEvent()
    data class SetShuffle(val shuffleModeEnabled: Boolean) : AudioEvent()
    data class SetVolume(val volume: Float): AudioEvent()
    data class SetRepeatMode(val repeatMode: Int) : AudioEvent()
    data class UpdateProgress(val newProgress: Float) : AudioEvent()
    data class PlayBackSpeed(val playbackSpeed: Float) : AudioEvent()
}