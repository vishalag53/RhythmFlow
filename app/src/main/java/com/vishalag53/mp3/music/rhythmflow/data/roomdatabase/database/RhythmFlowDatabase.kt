package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.dao.PlaybackDao
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.model.PlaybackState

@Database(
    entities = [PlaybackState::class, Audio::class],
    version = 1,
    exportSchema = false
)
abstract class RhythmFlowDatabase : RoomDatabase() {
    abstract fun playbackDao(): PlaybackDao

    companion object {
        @Volatile
        private var INSTANCE: RhythmFlowDatabase? = null

        fun getDatabase(context: Context): RhythmFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RhythmFlowDatabase::class.java,
                    "rhythmflow_db"
                ).build()
                    .also {
                    INSTANCE = it
                }
            }
        }
    }
}