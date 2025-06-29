package com.vishalag53.mp3.music.rhythmflow.presentation.main.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.main.appbar.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.SongsRootScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRootScreen(
    tab: String,
    navController: NavHostController,
    audioList: List<Audio>,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel,
    startNotificationService: () -> Unit
) {
    val mainUiStateSaver: Saver<MainUiState, *> = Saver(save = {
        listOf(it.isPlayingQueue)
    }, restore = {
        MainUiState(
            isPlayingQueue = it[0]
        )
    })
    val mainUiState = rememberSaveable(stateSaver = mainUiStateSaver) {
        mutableStateOf(MainUiState())
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }

    LaunchedEffect(mainUiState.value) {
        showSheet.value = mainUiState.value.isPlayingQueue
        sheetContent.value = when {
            mainUiState.value.isPlayingQueue -> {
                {
                    SongQueueListsItem(
                        mainViewModel = mainViewModel,
                        navController = navController
                    )
                }
            }

            else -> {
                {}
            }
        }
    }

    Scaffold(
        topBar = {
            AppBarRootScreen(navController = navController)
        },
        bottomBar = {
            if (smallPlayerViewModel.currentSelectedAudio.collectAsState().value.title != "") {
                SmallPlayerRootScreen(
                    audio = smallPlayerViewModel.currentSelectedAudio.collectAsState().value,
                    audioList = smallPlayerViewModel.audioList.collectAsState().value,
                    progress = smallPlayerViewModel.progress.collectAsState().value,
                    progressString = smallPlayerViewModel.progressString.collectAsState().value,
                    isAudioPlaying = smallPlayerViewModel.isPlaying.collectAsState().value,
                    onStart = { smallPlayerViewModel.onSmallPlayerEvents(SmallPlayerEvents.PlayPause) },
                    onNext = { smallPlayerViewModel.onSmallPlayerEvents(SmallPlayerEvents.SeekToNextItem) },
                    onPrev = { smallPlayerViewModel.onSmallPlayerEvents(SmallPlayerEvents.SeekToPreviousItem) },
                    index = smallPlayerViewModel.currentSelectedAudioIndex.collectAsState().value + 1,
                    onClick = {
                        mainUiState.value = mainUiState.value.copy(isPlayingQueue = true)
                    }
                )
            }
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
                K.SONGS -> SongsRootScreen(
                    navController = navController,
                    audioList = audioList,
                    mainViewModel = mainViewModel,
                    smallPlayerViewModel = smallPlayerViewModel,
                    startNotificationService = startNotificationService
                )
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                mainUiState.value = MainUiState()
                showSheet.value = false
            },
            sheetState = sheetState,
            containerColor = Color(0xFF736659),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.DarkGray)
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF736659))
            ) {
                sheetContent.value()
            }
        }
    }
}