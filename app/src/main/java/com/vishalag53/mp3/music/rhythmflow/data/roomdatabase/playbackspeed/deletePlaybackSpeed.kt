package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase

suspend fun deletePlaybackSpeed(context: Context, name: String) {
    RhythmFlowDatabase.getDatabase(context).playbackSpeedDao().deletePlaybackSpeed(name)
}