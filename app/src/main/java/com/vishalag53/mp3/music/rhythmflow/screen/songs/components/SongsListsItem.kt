package com.vishalag53.mp3.music.rhythmflow.screen.songs.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.screen.songs.songitem.SongItem
import com.vishalag53.mp3.music.rhythmflow.viewmodel.MainViewModel

@Composable
internal fun SongsListsItem(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val songs = viewModel.audioList
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.fillMaxSize()
    ) {
        items(songs) { song ->
            SongItem(song, navController)
        }
    }
}