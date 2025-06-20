package com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectTabBox(
    text: String,
    width: Dp,
    topEnd: Dp,
    topStart: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width / 2)
            .height(30.dp)
            .clip(RoundedCornerShape(topStart = topStart, topEnd = topEnd))
            .background(Color(0xFFFDCF9E))
            .border(
                width = 0.5.dp,
                color = Color(0xFF35363B),
                shape = RoundedCornerShape(topStart = topStart, topEnd = topEnd)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color(0xFF99826D),
            maxLines = 1
        )
    }
}