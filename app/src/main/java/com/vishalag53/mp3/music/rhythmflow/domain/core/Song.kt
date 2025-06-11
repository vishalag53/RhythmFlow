package com.vishalag53.mp3.music.rhythmflow.domain.core

import android.content.Context
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatDuration(duration: Long, context: Context): String {
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
        context.getString(R.string.duration_with_hour, hours, minutes, seconds)
    } else {
        context.getString(R.string.duration_without_hour, minutes, seconds)
    }
}

fun formatSize(size: Long, context: Context): String {
    val sizeMB = size / (1024.0 * 1024.0)
    return context.getString(R.string.size, sizeMB)
}

fun formatBitrate(bitrate: Long, context: Context): String {
    val bitrateKBPS = bitrate / 1000
    return context.getString(R.string.bitrate, bitrateKBPS)
}

fun formatDate(date: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.format(Date(date * 1000L))
}

fun totalAudioTime(audioList: List<Audio>, context: Context): String {
    var totalTime: Long = 0
    for (audio in audioList) {
        totalTime += audio.duration
    }
    return context.getString(R.string.total_time_text, formatDuration(totalTime, context))
}

fun stringCapitalized(string: String): String {
    return string.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

fun calculateProgressValue(
    currentProgress: Long,
    context: Context,
    duration: Long
): Pair<Float, String> {
    val progress =
        if (currentProgress > 0) ((currentProgress.toFloat() / duration.toFloat()) * 1f)
        else 0f
    val progressString = formatDuration(currentProgress, context)
    return Pair(progress, progressString)
}