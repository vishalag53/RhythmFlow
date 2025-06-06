package com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.songinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.formatBitrate
import com.vishalag53.mp3.music.rhythmflow.formatDate
import com.vishalag53.mp3.music.rhythmflow.formatDuration
import com.vishalag53.mp3.music.rhythmflow.formatSize
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.songinfo.components.AboutCard

@Composable
fun SongInfoRootScreen(audio: Audio) {
    val context = LocalContext.current
    val about = arrayOf(
        "Song Name",
        "Display Name",
        "Artist",
        "Album",
        "Duration",
        "Size",
        "Bit Rate",
        "Date Added",
        "Date Modified",
        "Folder Name",
        "File Path"
    )

    val aboutInfo = arrayOf(
        audio.title,
        audio.displayName,
        audio.artist,
        audio.album,
        formatDuration(audio.duration, context),
        formatSize(audio.size, context),
        formatBitrate(audio.bitrate, context),
        formatDate(audio.dateAdded),
        formatDate(audio.dateModified),
        audio.folderName,
        audio.path
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            for (i in about.indices) {
                AboutCard(about[i], aboutInfo[i])
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}