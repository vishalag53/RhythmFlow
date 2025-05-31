package com.vishalag53.mp3.music.rhythmflow.screen.player.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun PlayerBack() {
    Icon(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "Back",
        tint = Color(0xFF35363B),
        modifier = Modifier.size(28.dp)
    )
}