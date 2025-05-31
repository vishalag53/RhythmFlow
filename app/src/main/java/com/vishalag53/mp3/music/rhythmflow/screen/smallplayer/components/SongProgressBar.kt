package com.vishalag53.mp3.music.rhythmflow.screen.smallplayer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
internal fun SongProgressBar(progress: Float) {
    var currentProgress by remember { mutableFloatStateOf(progress) }
    LinearProgressIndicator(
        progress = { currentProgress },
        modifier = Modifier.fillMaxWidth()
            .clip(RectangleShape),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.secondary,
        gapSize = 0.dp
    )
}