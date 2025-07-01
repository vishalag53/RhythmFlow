package com.vishalag53.mp3.music.rhythmflow.navigation

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
    data object Search : Screens
}