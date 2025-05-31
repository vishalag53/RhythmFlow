package com.vishalag53.mp3.music.rhythmflow.screen.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.screen.songs.components.SongsListsItem
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.SongsTopBar

@Composable
fun SongsRootScreen(navController: NavHostController) {
    Scaffold(
        topBar = { SongsTopBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SongsListsItem(navController)
        }
    }
}