package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.model.PlaybackState

suspend fun getPlaybackState(context: Context): PlaybackState? {
    val db = RhythmFlowDatabase.getDatabase(context)
    val dao = db.playbackDao()

    val playbackState = dao.getPlaybackInfo()
    return playbackState
}