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
internal fun MusicDisplay(
    display: String,
    color: Color,
    softWrap: Boolean = true,
    overflow: TextOverflow,
    modifier: Modifier = Modifier
) {
    Text(
        text = display,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Light),
        color = color,
        maxLines = 1,
        fontSize = 14.sp,
        softWrap = softWrap,
        overflow = overflow,
        modifier = modifier
    )
}

