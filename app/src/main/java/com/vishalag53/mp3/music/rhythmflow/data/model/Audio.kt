package com.vishalag53.mp3.music.rhythmflow.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Audio(
    val id: String,
    val displayName: String,
    val title: String,
    val album: String,
    val dateAdded: Long,
    val dateModified: Long,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val folderName: String,
    val size: Long,
    val bitrate: Long
): Parcelable