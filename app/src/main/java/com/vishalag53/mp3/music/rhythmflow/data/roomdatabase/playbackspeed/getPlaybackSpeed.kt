package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun getPlaybackSpeed(context: Context, name: String): Flow<Float> {
    return RhythmFlowDatabase.getDatabase(context).playbackSpeedDao().getSongsPlaybackSpeed(name)
        .map { it?.playbackSpeed ?: 1.0f }
}