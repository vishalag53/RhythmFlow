package com.vishalag53.mp3.music.rhythmflow.presentation.player

sealed class PlayerBottomSheetContent {
    object None : PlayerBottomSheetContent()
    object PlayingQueue : PlayerBottomSheetContent()
    object SongInfo : PlayerBottomSheetContent()
    object PlaybackSpeed : PlayerBottomSheetContent()
}