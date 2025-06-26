package com.vishalag53.mp3.music.rhythmflow.data.local.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Serializable
@Parcelize
@Entity(tableName = "audio_table")
data class Audio(
    @PrimaryKey val id: String,
    val uriString: String,
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
) : Parcelable {
    val uri: Uri get() = uriString.toUri()
}