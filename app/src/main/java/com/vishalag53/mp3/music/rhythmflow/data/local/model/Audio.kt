package com.vishalag53.mp3.music.rhythmflow.data.local.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import androidx.core.net.toUri

@Serializable
@Parcelize
data class Audio(
    val uriString: String,
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
): Parcelable {
    val uri: Uri get() = uriString.toUri()
}