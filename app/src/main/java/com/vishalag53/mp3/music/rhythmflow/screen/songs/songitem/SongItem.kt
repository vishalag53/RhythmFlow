package com.vishalag53.mp3.music.rhythmflow.screen.songs.songitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.formatDuration
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicInfo
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTime
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.screen.songs.songitem.components.SongMusicIcon
import com.vishalag53.mp3.music.rhythmflow.stringCapitalized

@Composable
fun SongItem(song: Audio, navController: NavHostController) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                SongMusicIcon()

                Spacer(modifier = Modifier.width(8.dp))

                MusicTitleDisplayName(
                    stringCapitalized(song.title),
                    stringCapitalized(song.displayName),
                    MaterialTheme.colorScheme.primary,
                    true,
                    TextOverflow.Ellipsis,
                    Modifier.padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.width(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MusicTime(
                    formatDuration(song.duration, context), MaterialTheme.colorScheme.primary, 12.sp
                )
                MusicInfo("Song Info", MaterialTheme.colorScheme.primary, Modifier.size(24.dp))
            }
        }

    }
}