package com.vishalag53.mp3.music.rhythmflow.domain.core

import android.annotation.SuppressLint
import android.app.RecoverableSecurityException
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("DefaultLocale")
fun formatDuration(duration: Long): String {
    val hours = TimeUnit.HOURS.convert(duration, TimeUnit.MILLISECONDS)
    val minutes = TimeUnit.MINUTES.convert(
        duration, TimeUnit.MILLISECONDS
    ) - hours * TimeUnit.MINUTES.convert(1, TimeUnit.HOURS)
    val seconds = (TimeUnit.SECONDS.convert(
        duration, TimeUnit.MILLISECONDS
    ) - hours * TimeUnit.SECONDS.convert(1, TimeUnit.HOURS) - minutes * TimeUnit.SECONDS.convert(
        1, TimeUnit.MINUTES
    ))
    return if (hours > 0) {
        String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds)
    } else {
        String.format("%1$02d:%2$02d", minutes, seconds)
    }
}

@SuppressLint("DefaultLocale")
fun formatSize(size: Long): String {
    val sizeMB = size / (1024.0 * 1024.0)
    return if (sizeMB < 1024) {
        String.format("%.2f MB", sizeMB)
    } else {
        val sizeGB = sizeMB / 1024.0
        String.format("%.2f GB", sizeGB)
    }
}

@SuppressLint("DefaultLocale")
fun formatBitrate(bitrate: Long): String = String.format("%d kbps", bitrate / 1000)

fun formatDate(date: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.format(Date(date * 1000L))
}

fun totalAudioTime(audioList: List<Audio>): String = formatDuration(audioList.sumOf { it.duration })

fun stringCapitalized(string: String): String = string.split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

fun calculateProgressValue(
    currentProgress: Long,
    duration: Long
): Pair<Float, String> {
    val progress =
        if (currentProgress > 0) ((currentProgress.toFloat() / duration.toFloat()) * 1f)
        else 0f
    val progressString = formatDuration(currentProgress)
    return Pair(progress, progressString)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun requestRenamePermission(
    newDisplayName: String,
    audio: Audio,
    context: Context,
    onPermissionGranted: (IntentSender) -> Unit,
    onRenameSuccess: () -> Unit
) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, newDisplayName)
    }

    try {
        val rowsUpdated = context.contentResolver.update(audio.uri, contentValues, null, null)
        if (rowsUpdated > 0) onRenameSuccess()
    } catch (e: RecoverableSecurityException) {
        onPermissionGranted(e.userAction.actionIntent.intentSender)
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

fun renameDisplayName(editDisplayName: String, audio: Audio, context: Context) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, editDisplayName)
    }

    context.contentResolver.update(audio.uri, contentValues, null, null)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun requestDeletePermission(
    audio: Audio,
    context: Context,
    onPermissionGranted: (IntentSender) -> Unit,
    onDeleteSuccess: () -> Unit
) {
    try {
        val rowsUpdated = context.contentResolver.delete(audio.uri, null, null)
        if (rowsUpdated > 0) onDeleteSuccess()
    } catch (e: RecoverableSecurityException) {
        onPermissionGranted(e.userAction.actionIntent.intentSender)
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun requestDeletePermissionForList(
    audioList: List<Audio>,
    context: Context,
    onPermissionGranted: (IntentSender) -> Unit,
    onDeleteSuccess: () -> Unit
) {
    if (audioList.isEmpty()) {
        onDeleteSuccess()
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val needingConsentUris = mutableListOf<Uri>()
        val deleteNow = mutableListOf<Audio>()

        audioList.forEach { audio ->
            try {
                val rows = context.contentResolver.delete(audio.uri, null, null)
                if (rows > 0) deleteNow.add(audio)
            } catch (e: RecoverableSecurityException) {
                needingConsentUris.add(audio.uri)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        if (needingConsentUris.isEmpty()) {
            onDeleteSuccess()
        } else {
            val pendingIntent =
                MediaStore.createDeleteRequest(context.contentResolver, needingConsentUris)
            onPermissionGranted(pendingIntent.intentSender)
        }
    } else {
        val first = audioList.first()
        requestDeletePermission(
            audio = first,
            context = context,
            onPermissionGranted = { sender -> onPermissionGranted(sender)},
            onDeleteSuccess = { onDeleteSuccess()}
        )
    }
}

fun deleteAudioFile(audio: Audio, context: Context) {
    try {
        context.contentResolver.delete(audio.uri, null, null)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}