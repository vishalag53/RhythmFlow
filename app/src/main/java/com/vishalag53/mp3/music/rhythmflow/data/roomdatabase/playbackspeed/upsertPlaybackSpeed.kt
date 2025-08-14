package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.model.PlaybackSpeedModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun upsertPlaybackSpeed(
    name: String,
    playbackSpeed: Float,
    context: Context
) {
    CoroutineScope(Dispatchers.IO).launch {
        val playbackSpeedModel = PlaybackSpeedModel(
            name = name,
            playbackSpeed = playbackSpeed
        )

        RhythmFlowDatabase.getDatabase(context).playbackSpeedDao()
            .upsertSongsPlaybackSpeed(playbackSpeedModel)
    }
}