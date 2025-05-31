package com.vishalag53.mp3.music.rhythmflow.screen.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicNextPlay
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPlayPause
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPreviousPlay

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

            MusicPreviousPlay(
                painterResource(R.drawable.ic_player_previous_music),
                Color(0xFFFDCF9E),
                Modifier.size(24.dp)
            )

            PlayerRewind(
                painterResource(R.drawable.ic_rewind_10_second_back),
                Color(0xFFFDCF9E),
                Modifier.size(36.dp)
            )

            MusicPlayPause(
                painterResource(R.drawable.ic_music_play),
                Color(0xFFFDCF9E),
                Modifier.size(52.dp)
            )

            PlayerRewind(
                painterResource(R.drawable.ic_rewind_10_second_forward),
                Color(0xFFFDCF9E),
                Modifier.size(36.dp)
            )

            MusicNextPlay(
                painterResource(R.drawable.ic_player_next_music),
                Color(0xFFFDCF9E),
                Modifier.size(24.dp)
            )
        }
    }
}