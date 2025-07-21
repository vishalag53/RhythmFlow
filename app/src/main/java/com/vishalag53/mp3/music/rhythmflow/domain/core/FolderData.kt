package com.vishalag53.mp3.music.rhythmflow.domain.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FolderData(
    val name: String,
    val path: String,
    val length: Int,
    val totalTime: Long,
    val totalSize: Long
) : Parcelable