package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.model.PlaybackSpeedModel
import kotlinx.coroutines.flow.Flow

fun getAllPlaybackSpeeds(context: Context): Flow<List<PlaybackSpeedModel>> {
    return RhythmFlowDatabase.getDatabase(context).playbackSpeedDao().getAllPlaybackSpeeds()
}