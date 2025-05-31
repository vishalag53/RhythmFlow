package com.vishalag53.mp3.music.rhythmflow.screen.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
internal fun MusicNextPlay(painter: Painter, tint: Color, modifier: Modifier) {
    Icon(
        painter = painter,
        contentDescription = "Music play next",
        tint = tint,
        modifier = modifier
    )
}