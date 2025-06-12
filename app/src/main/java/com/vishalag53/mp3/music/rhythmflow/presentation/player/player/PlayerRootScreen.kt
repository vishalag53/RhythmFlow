package com.vishalag53.mp3.music.rhythmflow.presentation.player.player

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
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioProgressBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerControllers
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerTopBar
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.playersheet.components.PlayerBottomSheet

@Composable
fun PlayerRootScreen(audio: Audio, navigateBack: () -> Unit, mainViewModel: MainViewModel) {
    Scaffold(
        topBar = {
            PlayerTopBar(navigateBack)
        },
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
                        AudioTitleDisplayName(
                            title = stringCapitalized(audio.title),
                            display = stringCapitalized(audio.displayName),
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
                    AudioProgressBar(Color(0xFF35363B), audio)
                    Spacer(modifier = Modifier.height(10.dp))
                    PlayerControllers()
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .height(126.dp)
                        .fillMaxWidth()
                ) {
                    PlayerBottomSheet(audio = audio, mainViewModel = mainViewModel)
                }
            }
        }
    }
}