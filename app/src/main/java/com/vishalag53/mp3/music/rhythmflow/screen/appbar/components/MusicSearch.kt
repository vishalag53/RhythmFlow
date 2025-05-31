package com.vishalag53.mp3.music.rhythmflow.screen.appbar.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun MusicSearch() {
    Icon(
        painter = painterResource(R.drawable.ic_music_search),
        contentDescription = "Music Search",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(26.dp)
    )
}