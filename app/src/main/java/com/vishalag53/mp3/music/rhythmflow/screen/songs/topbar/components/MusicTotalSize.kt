package com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components

import android.content.Context
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.totalSongTime

@Composable
internal fun MusicTotalSize(songs: List<Audio>, context: Context) {
    Text(
        text = "(${songs.size})${totalSongTime(songs, context)}",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Visible,
        modifier = Modifier
            .fillMaxWidth()
            .basicMarquee()
    )
}