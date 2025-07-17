package com.vishalag53.mp3.music.rhythmflow.presentation.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.components.SongsTopBar

@Composable
fun SongsRootScreen(
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    onMenuClick: () -> Unit,
    menuViewModel: MenuViewModel,
    onSortByClick: () -> Unit,
    mainViewModel: MainViewModel,
    parentUiState: MutableState<ParentUiState>
) {
    val audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = {
            SongsTopBar(
                audioList = audioList,
                refreshAudioList = { mainViewModel.refreshAudioList() },
                onSortByClick = onSortByClick,
                parentUiState = parentUiState
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