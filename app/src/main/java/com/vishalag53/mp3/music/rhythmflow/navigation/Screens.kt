package com.vishalag53.mp3.music.rhythmflow.navigation

import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    sealed interface Main : Screens {
        @Serializable
        data object Songs : Screens
    }

    @Serializable
    data object Player : Screens

    @Serializable
    data class Folder(val folder: FolderData) : Screens

    @Serializable
    data object PlaybackSpeed : Screens

    @Serializable
    data object Settings : Screens
}