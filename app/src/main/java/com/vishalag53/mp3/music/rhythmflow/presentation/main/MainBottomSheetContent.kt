package com.vishalag53.mp3.music.rhythmflow.presentation.main

sealed class MainBottomSheetContent {
    object None: MainBottomSheetContent()
    object PlayingQueue: MainBottomSheetContent()
}