package com.vishalag53.mp3.music.rhythmflow.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MusicTitleDisplayName(
    title: String,
    display: String,
    color: Color,
    softWrap: Boolean,
    overflow: TextOverflow,
    modifierColumn: Modifier = Modifier,
    modifierText: Modifier = Modifier
) {
    Column(
        modifier = modifierColumn,
        verticalArrangement = Arrangement.Center
    ) {
        MusicTitle(title, color, softWrap, overflow, modifierText)
        MusicDisplay(display, color, softWrap, overflow, modifierText)
    }
}