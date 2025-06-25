package com.vishalag53.mp3.music.rhythmflow.data.local.repository

import com.vishalag53.mp3.music.rhythmflow.data.local.contentresolverhelper.ContentResolverHelper
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject constructor(private val contentResolverHelper: ContentResolverHelper) {
    suspend fun getAudioData(): List<Audio> = withContext(Dispatchers.IO) {
        contentResolverHelper.getAudioData()
    }
}