package com.vishalag53.mp3.music.rhythmflow.presentation.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.components.SongsTopBar

@Composable
fun SongsRootScreen(
    navController: NavHostController,
    audioList: List<Audio>,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    onMenuClick: () -> Unit,
    menuViewModel: MenuViewModel,
    refreshAudioList: () -> Unit,
    onSortByClick: () -> Unit
) {
    Scaffold(
        topBar = {
            SongsTopBar(
                audioList = audioList,
                refreshAudioList = refreshAudioList,
                onSortByClick = onSortByClick
            )
        },
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
                itemsIndexed(audioList) { index, audio ->
                    AudioItem(
                        audio = audio,
                        audioList = audioList,
                        navController = navController,
                        basePlayerViewModel = basePlayerViewModel,
                        index = index,
                        startNotificationService = startNotificationService,
                        onMenuClick = onMenuClick,
                        menuViewModel = menuViewModel
                    )
                }
            }
        }
    }
}