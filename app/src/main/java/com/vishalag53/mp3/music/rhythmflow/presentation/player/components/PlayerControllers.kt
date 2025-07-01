package com.vishalag53.mp3.music.rhythmflow.presentation.player.components

import androidx.compose.foundation.clickable
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
fun PlayerControllers(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onBackward: () -> Unit,
    onForward: () -> Unit,
    onStart: () -> Unit,
    isAudioPlaying: Boolean
) {
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
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onPrev()
                    }
            )

            Icon(
                painter = painterResource(R.drawable.ic_rewind_10_second_back),
                contentDescription = null,
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onBackward()
                    }
            )

            Icon(
                painter = if (isAudioPlaying) painterResource(R.drawable.ic_pause)
                else painterResource(R.drawable.ic_play),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(52.dp)
                    .clickable {
                        onStart()
                    }
            )

            Icon(
                painter = painterResource(R.drawable.ic_rewind_10_second_forward),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onForward()
                    }
            )

            Icon(
                painter = painterResource(R.drawable.ic_player_next),
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onNext()
                    }
            )
        }
    }
}