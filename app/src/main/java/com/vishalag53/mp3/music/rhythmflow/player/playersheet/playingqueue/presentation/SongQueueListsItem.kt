package com.vishalag53.mp3.music.rhythmflow.player.playersheet.playingqueue.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SongQueueListsItem() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 6.dp)) {
        items(100) {
            SongQueueItem()
        }
    }
}