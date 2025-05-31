package com.vishalag53.mp3.music.rhythmflow.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicProgressBar
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.screen.player.components.PlayerControllers
import com.vishalag53.mp3.music.rhythmflow.screen.player.components.PlayerPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.screen.player.components.PlayerTopBar
import com.vishalag53.mp3.music.rhythmflow.screen.player.playermodalsheet.PlayerBottomSheet
import com.vishalag53.mp3.music.rhythmflow.stringCapitalized

@Composable
fun PlayerRootScreen() {
    Scaffold(
        topBar = { PlayerTopBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFFFDCF9E),
                            0.41f to Color(0xFF99826D),
                            1.0f to Color(0xFF35363B)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MusicTitleDisplayName(
                            title = stringCapitalized("song.title"),
                            display = stringCapitalized("song.displayName"),
                            color = Color(0xFFFDCF9E),
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            modifierColumn = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        PlayerPlaybackSpeed()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    MusicProgressBar(Color(0xFF35363B))
                    Spacer(modifier = Modifier.height(10.dp))
                    PlayerControllers()
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .height(126.dp)
                        .fillMaxWidth()
                ) {
                    PlayerBottomSheet()
                }
            }
        }
    }
}