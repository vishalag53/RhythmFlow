package com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer

sealed class BasePlayerEvents {
    object PlayPause : BasePlayerEvents()
    object SeekToNextItem : BasePlayerEvents()
    object SeekToPreviousItem : BasePlayerEvents()
    object Forward : BasePlayerEvents()
    object Backward : BasePlayerEvents()
    object ClearMediaItems : BasePlayerEvents()

    data class SetShuffle(val shuffleModeEnabled: Boolean) : BasePlayerEvents()
    data class SetVolume(val volume: Float): BasePlayerEvents()
    data class SetRepeatMode(val repeatMode: Int) : BasePlayerEvents()
    data class PlayBackSpeed(val playbackSpeed: Float) : BasePlayerEvents()
    data class UpdateProgress(val newProgress: Float) : BasePlayerEvents()
    data class SelectedAudioChange(val index: Int) : BasePlayerEvents()
}