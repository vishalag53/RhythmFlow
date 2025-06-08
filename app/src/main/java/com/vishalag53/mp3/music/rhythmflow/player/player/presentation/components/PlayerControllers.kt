package com.vishalag53.mp3.music.rhythmflow.player.player.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
fun PlayerControllers() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                painter = painterResource(R.drawable.ic_rewind_10_second_back),
                contentDescription = null,
                tint = Color(0xFFFDCF9E),
                modifier = Modifier.size(36.dp)
            )

            Icon(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier.size(52.dp)
            )

            Icon(
                painter = painterResource(R.drawable.ic_rewind_10_second_forward),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier.size(36.dp)
            )

            Icon(
                painter = painterResource(R.drawable.ic_player_next),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}