package com.vishalag53.mp3.music.rhythmflow.presentation.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = null,
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onPrev()
                    }
            )

            Icon(
                imageVector = Icons.Default.Replay10,
                contentDescription = null,
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onBackward()
                    }
            )

            Icon(
                imageVector = if (isAudioPlaying) Icons.Default.PauseCircle
                else Icons.Default.PlayCircle,
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(52.dp)
                    .clickable {
                        onStart()
                    }
            )

            Icon(
                imageVector = Icons.Default.Forward10,
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        onForward()
                    }
            )

            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = "Music play previous",
                tint = Color(0xFFFDCF9E),
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onNext()
                    }
            )
        }
    }
}