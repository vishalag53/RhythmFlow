package com.vishalag53.mp3.music.rhythmflow.presentation.parent

sealed class ParentBottomSheetContent {
    data object None : ParentBottomSheetContent()
    data object PlaybackSpeed : ParentBottomSheetContent()
    data object PlayingQueue: ParentBottomSheetContent()
    data object SongInfo: ParentBottomSheetContent()
    data object Menu: ParentBottomSheetContent()
    data object FolderMenu: ParentBottomSheetContent()
    data object Repeat: ParentBottomSheetContent()
    data object SearchTabSelector: ParentBottomSheetContent()
    data object SortAudioBy: ParentBottomSheetContent()
    data object SortFolderBy: ParentBottomSheetContent()
    data object SelectTabName: ParentBottomSheetContent()
}