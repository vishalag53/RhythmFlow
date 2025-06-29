package com.vishalag53.mp3.music.rhythmflow.presentation.search

sealed class SearchBottomSheetContent {
    object None: SearchBottomSheetContent()
    object PlayingQueue: SearchBottomSheetContent()
    object TabSelector: SearchBottomSheetContent()
}