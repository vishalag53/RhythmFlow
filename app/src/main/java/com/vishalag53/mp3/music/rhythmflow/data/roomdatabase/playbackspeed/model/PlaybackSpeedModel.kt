package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playback_speed")
data class PlaybackSpeedModel(
    @PrimaryKey val name: String,
    val playbackSpeed: Float
)