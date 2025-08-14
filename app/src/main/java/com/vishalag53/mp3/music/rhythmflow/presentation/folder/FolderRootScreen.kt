package com.vishalag53.mp3.music.rhythmflow.presentation.folder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.SongsRootScreen

@Composable
fun FolderRootScreen(
    folder: FolderData,
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    mainViewModel: MainViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel,
) {
    Scaffold(
        topBar = {
            FolderTopBar(
                folderName = folder.name,
                navController = navController
            )
        },
        bottomBar = {
            if (basePlayerViewModel.currentSelectedAudio.collectAsState().value.title != "") {
                SmallPlayerRootScreen(
                    audio = basePlayerViewModel.currentSelectedAudio.collectAsState().value,
                    audioList = basePlayerViewModel.audioList.collectAsState().value,
                    progress = basePlayerViewModel.progress.collectAsState().value,
                    progressString = basePlayerViewModel.progressString.collectAsState().value,
                    isAudioPlaying = basePlayerViewModel.isPlaying.collectAsState().value,
                    onStart = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.PlayPause) },
                    onNext = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToNextItem) },
                    onPrev = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToPreviousItem) },
                    index = basePlayerViewModel.currentSelectedAudioIndex.collectAsState().value + 1,
                    onClick = {
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.PlayingQueue)
                    },
                    onOpenPlayer = {
                        navController.navigate(Screens.Player)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SongsRootScreen(
                audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value.filter { it.folderName == folder.name },
                navController = navController,
                startNotificationService = startNotificationService,
                basePlayerViewModel = basePlayerViewModel,
                onMenuClick = {
                    menuViewModel.setMenuFrom(K.FOLDERS)
                    parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                },
                menuViewModel = menuViewModel,
                onSortByClick = {
                    parentUiState.value = ParentUiState(ParentBottomSheetContent.SortAudioBy)
                },
                mainViewModel = mainViewModel,
                parentUiState = parentUiState,
                parentViewModel = parentViewModel,
            )
        }
    }
}