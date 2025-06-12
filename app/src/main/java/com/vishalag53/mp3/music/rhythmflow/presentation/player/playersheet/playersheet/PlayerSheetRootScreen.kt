package com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.playersheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.miniplayer.PlayerMiniPlayer
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.playersheet.components.PlayerBottomSheet

@Composable
fun PlayerSheetRootScreen(
    audio: Audio,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
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
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            PlayerMiniPlayer(audio)
            Spacer(modifier = Modifier.height(12.dp))
            PlayerBottomSheet(audio, mainViewModel, navController)
        }
    }
}