package com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.presentation.core.PlayerQueue
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.components.SmallPlayerController
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.components.SmallPlayerTime

@Composable
fun SmallPlayerRootScreen(
    audio: Audio,
    progress: Float,
    progressString: String,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    audioList: List<Audio>,
    index: Int,
    onClick: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background, Color(0xFF3078AB)
                    )
                )
            )
            .clickable {
                onOpenPlayer()
            }
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondary,
            gapSize = 0.dp
        )

        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))
            SmallPlayerTime(progressString, formatDuration(audio.duration))
            Spacer(modifier = Modifier.height(3.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AudioTitleDisplayName(
                    title = stringCapitalized(audio.title),
                    display = stringCapitalized(audio.displayName),
                    color = MaterialTheme.colorScheme.primary,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                    modifierColumn = Modifier
                        .padding(start = 2.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallPlayerController(
                        onNext = onNext,
                        onPrev = onPrev,
                        onStart = onStart,
                        isAudioPlaying = isAudioPlaying
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    PlayerQueue(
                        index = index,
                        length = audioList.size,
                        onClick = onClick,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}