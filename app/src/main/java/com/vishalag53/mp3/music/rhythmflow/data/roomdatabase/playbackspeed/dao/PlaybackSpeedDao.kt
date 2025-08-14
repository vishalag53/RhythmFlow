package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.model.PlaybackSpeedModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaybackSpeedDao {

    @Upsert
    suspend fun upsertSongsPlaybackSpeed(speedEntity: PlaybackSpeedModel)

    @Query("SELECT * FROM playback_speed WHERE name = :name")
    fun getSongsPlaybackSpeed(name: String): Flow<PlaybackSpeedModel?>

    @Query("SELECT * FROM playback_speed")
    fun getAllPlaybackSpeeds(): Flow<List<PlaybackSpeedModel>>

    @Query("DELETE FROM playback_speed WHERE name = :name")
    suspend fun deletePlaybackSpeed(name: String)

    @Query("DELETE FROM playback_speed")
    suspend fun deleteAllPlaybackSpeeds()
}