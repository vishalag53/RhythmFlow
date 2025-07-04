package com.vishalag53.mp3.music.rhythmflow.presentation.player

sealed class PlayerBottomSheetContent {
    object None : PlayerBottomSheetContent()
    object PlayerQueue : PlayerBottomSheetContent()
    object SongInfo : PlayerBottomSheetContent()
    object PlaybackSpeed : PlayerBottomSheetContent()
    object PlayingMenu : PlayerBottomSheetContent()
}