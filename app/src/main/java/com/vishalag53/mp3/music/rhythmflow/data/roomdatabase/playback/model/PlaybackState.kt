package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vishalag53.mp3.music.rhythmflow.domain.core.K

@Entity(tableName = "playback_state")
data class PlaybackState(
    @PrimaryKey val id:Int = 1,
    val currentAudioId: String,
    val currentPosition: Long,
    val queueName: String = K.QUEUE_SONG_ALL
)
