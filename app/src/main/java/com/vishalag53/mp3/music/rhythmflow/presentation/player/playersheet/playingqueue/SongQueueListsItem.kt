package com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.playingqueue

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel

@Composable
internal fun SongQueueListsItem(mainViewModel: MainViewModel, navController: NavHostController) {
    val audioList = mainViewModel.audioListPlayer.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        items(audioList) { audio ->
            SongQueueItem(
                audio = audio,
                audioList = audioList,
                mainViewModel = mainViewModel,
                navController = navController
            )
        }
    }
}