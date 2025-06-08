package com.vishalag53.mp3.music.rhythmflow.data.contentresolverhelper

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ContentResolverHelper @Inject
constructor(@ApplicationContext val context: Context) {
    private var mCursor: Cursor? = null

    private val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }
    private val projection: Array<String> = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.DATE_MODIFIED,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.SIZE,
        MediaStore.Audio.Media.BITRATE
    )

    private val selectionClause = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    private val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

    @WorkerThread
    fun getAudioData(): List<Audio> {
        return getCursorData()
    }

    private fun getCursorData(): MutableList<Audio> {
        val audioList = mutableListOf<Audio>()

        mCursor = context.contentResolver.query(
            collection,
            projection,
            selectionClause,
            null,
            sortOrder
        )

        mCursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val titleC =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val idC = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val displayNameC =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val albumC =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))
                val artistC =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val pathC =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val dateAddedC =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED))
                val dateModifiedC =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED))
                val durationC =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                val bitrateC =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.BITRATE))
                val sizeC =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val pathCC: List<String> = pathC.split("/")
                val folderName = pathCC[pathCC.size - 2]

                if (durationC > 0 && File(pathC).exists()) {
                    val song = Audio(
                        id = idC,
                        displayName = displayNameC,
                        title = titleC,
                        album = albumC,
                        dateAdded = dateAddedC,
                        dateModified = dateModifiedC,
                        artist = artistC,
                        path = pathC,
                        duration = durationC,
                        folderName = folderName,
                        size = sizeC,
                        bitrate = bitrateC
                    )
                    audioList.add(song)
                }
            }
        }
        return audioList
    }

}