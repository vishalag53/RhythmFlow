package com.vishalag53.mp3.music.rhythmflow.screen.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
internal fun MusicTitle(
    title: String,
    color: Color,
    softWrap: Boolean = true,
    overflow: TextOverflow,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
        color = color,
        maxLines = 1,
        fontSize = 20.sp,
        softWrap = softWrap,
        overflow = overflow,
        modifier = modifier
    )
}