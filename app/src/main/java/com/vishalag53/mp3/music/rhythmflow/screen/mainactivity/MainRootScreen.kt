package com.vishalag53.mp3.music.rhythmflow.screen.mainactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.screen.appbar.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.screen.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.screen.songs.SongsRootScreen

@Composable
fun MainRootScreen(
    tab: String, navController: NavHostController
) {
    Scaffold(topBar = { AppBarRootScreen() }, bottomBar = {
        SmallPlayerRootScreen(navController = navController)
    }) { innerPadding ->
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
                tab -> SongsRootScreen(navController)
            }
        }
    }
}