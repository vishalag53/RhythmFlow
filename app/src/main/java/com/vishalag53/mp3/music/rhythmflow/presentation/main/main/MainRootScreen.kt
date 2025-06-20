package com.vishalag53.mp3.music.rhythmflow.presentation.main.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.main.appbar.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.SongsRootScreen

@Composable
fun MainRootScreen(
    tab: String,
    navController: NavHostController,
    audioList: List<Audio>,
    mainViewModel: MainViewModel
) {
    val smallPlayerViewModel = hiltViewModel<SmallPlayerViewModel>()

    LaunchedEffect(Unit) {
        smallPlayerViewModel.setAudioList(audioList)
    }

    Scaffold(
        topBar = { AppBarRootScreen() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background, Color(0xFF3078AB)
                        )
                    )
                )
        ) {
            when (tab) {
                K.SONGS -> SongsRootScreen(
                    navController = navController,
                    audioList = audioList,
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}