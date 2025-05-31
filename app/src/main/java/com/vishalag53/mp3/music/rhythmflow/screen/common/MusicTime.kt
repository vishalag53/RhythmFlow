package com.vishalag53.mp3.music.rhythmflow.screen.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
internal fun MusicTime(time: String, color: Color, fontSize: TextUnit) {
    Text(
        text = time,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
        color = color,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Visible
    )
}