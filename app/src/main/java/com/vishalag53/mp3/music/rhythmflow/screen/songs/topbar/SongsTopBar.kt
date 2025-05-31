package com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishalag53.mp3.music.rhythmflow.viewmodel.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components.MusicPlayAllText
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components.MusicPlayIcon
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components.MusicTotalSize
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components.SongsShuffle
import com.vishalag53.mp3.music.rhythmflow.screen.songs.topbar.components.SongsSort

@Composable
fun SongsTopBar() {
    val viewModel = hiltViewModel<MainViewModel>()
    val songs = viewModel.audioList
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                MusicPlayIcon()
                Spacer(modifier = Modifier.width(8.dp))
                MusicPlayAllText()
                Spacer(modifier = Modifier.width(2.dp))
                MusicTotalSize(songs, context)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SongsSort()
                Spacer(modifier = Modifier.width(8.dp))
                SongsShuffle()
            }
        }
    }
}