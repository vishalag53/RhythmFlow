package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlayerQueue(
    index: Int,
    length: Int,
    onClick: () -> Unit,
    color: Color
) {
    Column(
        modifier = Modifier
            .width(48.dp)
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "$index/$length",
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontSize = 10.sp,
        )
    }
}