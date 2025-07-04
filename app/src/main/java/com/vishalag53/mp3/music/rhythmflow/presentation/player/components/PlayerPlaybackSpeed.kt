package com.vishalag53.mp3.music.rhythmflow.presentation.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun PlayerPlaybackSpeed(onOpen: () -> Unit, playbackSpeed: Float) {
    Column(
        modifier = Modifier
            .width(48.dp)
            .clickable {
                onOpen()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_playback_speed),
            contentDescription = null,
            tint = Color(0xFFFDCF9E),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "${playbackSpeed}x",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFFFDCF9E),
            fontSize = 18.sp
        )
    }
}