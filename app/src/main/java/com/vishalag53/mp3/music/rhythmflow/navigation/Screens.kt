package com.vishalag53.mp3.music.rhythmflow.navigation

import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    sealed interface Main : Screens {
        @Serializable
        data object Songs : Screens
    }

    @Serializable
    data class Player(val audio: Audio) : Screens

    @Serializable
    data object Search : Screens
}