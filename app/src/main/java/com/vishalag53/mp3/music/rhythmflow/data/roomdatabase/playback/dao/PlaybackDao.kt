package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.model.PlaybackState

@Dao
interface PlaybackDao {

    @Upsert
    suspend fun upsertPlaybackInfo(playbackState: PlaybackState)

    @Query("SELECT * FROM playback_state WHERE id = 1")
    suspend fun getPlaybackInfo(): PlaybackState?
}