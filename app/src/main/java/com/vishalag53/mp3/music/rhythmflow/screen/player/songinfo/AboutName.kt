package com.vishalag53.mp3.music.rhythmflow.screen.player.songinfo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun AboutName(about: String) {
    Text(
        text = about,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
        color = Color(0xFF35363B),
        fontSize = 16.sp
    )
}