package com.vishalag53.mp3.music.rhythmflow.data.repository

import com.vishalag53.mp3.music.rhythmflow.data.contentresolverhelper.ContentResolverHelper
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject constructor(private val contentResolverHelper: ContentResolverHelper) {
    suspend fun getAudioData(): List<Audio> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }
}