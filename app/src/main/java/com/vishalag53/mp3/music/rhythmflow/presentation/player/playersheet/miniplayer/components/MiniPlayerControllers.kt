package com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.miniplayer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
fun MiniPlayerControllers() {
    Row(
        modifier = Modifier.width(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_player_previous),
            contentDescription = null,
            tint = Color(0xFFFDCF9E),
            modifier = Modifier.size(24.dp)
        )

        Icon(
            painter = if (true) painterResource(R.drawable.ic_play)
            else painterResource(R.drawable.ic_pause),
            contentDescription = null,
            tint = Color(0xFFFDCF9E),
            modifier = Modifier.size(44.dp)
        )

        Icon(
            painter = painterResource(R.drawable.ic_player_next),
            contentDescription = null,
            tint = Color(0xFFFDCF9E),
            modifier = Modifier.size(24.dp)
        )
    }
}