package com.vishalag53.mp3.music.rhythmflow.screen.songs.songitem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun SongMusicIcon() {
    Icon(
        painter = painterResource(R.drawable.ic_music_icon),
        contentDescription = "Music Icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(60.dp)
            .padding(vertical = 6.dp)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(12.dp)
    )
}