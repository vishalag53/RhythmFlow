package com.vishalag53.mp3.music.rhythmflow.screen.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun MusicInfo(contentDescription: String, tint : Color, modifier: Modifier) {
    Icon(
        painter = painterResource(R.drawable.ic_menu),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
    )
}