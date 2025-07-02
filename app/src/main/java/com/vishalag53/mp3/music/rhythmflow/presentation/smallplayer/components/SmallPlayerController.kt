package com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
fun SmallPlayerController(
    onNext: () -> Unit, onPrev: () -> Unit, onStart: () -> Unit, isAudioPlaying: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_play_previous),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .width(17.dp)
                .height(20.dp)
                .clickable {
                    onPrev()
                })

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = if (isAudioPlaying) painterResource(R.drawable.ic_pause)
            else painterResource(R.drawable.ic_play),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(46.dp)
                .clickable {
                    onStart()
                })

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            painter = painterResource(R.drawable.ic_play_next),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .width(17.dp)
                .height(20.dp)
                .clickable {
                    onNext()
                })
    }
}