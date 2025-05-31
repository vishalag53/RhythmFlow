package com.vishalag53.mp3.music.rhythmflow.screen.smallplayer.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun SongPosition(position: String) {
    Text(
        text = position,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 10.sp,
        lineHeight = 22.sp
    )
}