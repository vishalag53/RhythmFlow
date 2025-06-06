package com.vishalag53.mp3.music.rhythmflow.presentation.main.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.components.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.components.SongsTopBar

@Composable
fun SongsRootScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<MainViewModel>()
    val songs = viewModel.audioList

    Scaffold(
        topBar = { SongsTopBar() },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
            ) {
                items(songs) { song ->
                    AudioItem(song, navController)
                }
            }
        }
    }
}