package com.vishalag53.mp3.music.rhythmflow.presentation.main

sealed class MainBottomSheetContent {
    object None: MainBottomSheetContent()
    object PlayingQueue: MainBottomSheetContent()
    object Menu: MainBottomSheetContent()
    object Repeat: MainBottomSheetContent()
    object SongInfo: MainBottomSheetContent()
    object SortBy: MainBottomSheetContent()
    object SelectTabName: MainBottomSheetContent()
}