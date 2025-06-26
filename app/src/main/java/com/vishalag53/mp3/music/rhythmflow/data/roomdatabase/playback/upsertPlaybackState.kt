package com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.database.RhythmFlowDatabase
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playback.model.PlaybackState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun upsertPlaybackState(
    audio: Audio,
    currentPosition: Long,
    queueName: String,
    context: Context
) {
    val db = RhythmFlowDatabase.getDatabase(context)
    val dao = db.playbackDao()

    CoroutineScope(Dispatchers.IO).launch {
        val playbackState = PlaybackState(
            currentAudioId = audio.id,
            currentPosition = currentPosition,
            queueName = queueName
        )

        dao.upsertPlaybackInfo(playbackState)
    }
}