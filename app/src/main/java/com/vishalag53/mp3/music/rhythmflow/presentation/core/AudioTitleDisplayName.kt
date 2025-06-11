package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AudioTitleDisplayName(
    title: String,
    display: String,
    color: Color,
    softWrap: Boolean,
    overflow: TextOverflow,
    modifierColumn: Modifier = Modifier,
) {
    Column(
        modifier = modifierColumn,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            color = color,
            maxLines = 1,
            softWrap = softWrap,
            overflow = overflow
        )

        Text(
            text = display,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
            color = color,
            maxLines = 1,
            softWrap = softWrap,
            overflow = overflow
        )
    }
}