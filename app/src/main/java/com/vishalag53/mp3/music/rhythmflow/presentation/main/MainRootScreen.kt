package com.vishalag53.mp3.music.rhythmflow.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.drawer.Drawer
import com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu.FolderMenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.folders.FoldersRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.components.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.SongsRootScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainRootScreen(
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    mainViewModel: MainViewModel,
    parentUiState: MutableState<ParentUiState>,
    folderMenuViewModel: FolderMenuViewModel
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(width = screenWidth * 0.8f)
        }
    ) {
        Scaffold(
            topBar = {
                AppBarRootScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    onSelectTabMainClick  = {
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.SelectTabName)
                    },
                    onNavigationDrawerClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }
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
                when (mainViewModel.selectTabName.collectAsStateWithLifecycle().value) {
                    K.SONGS -> SongsRootScreen(
                        audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value,
                        navController = navController,
                        basePlayerViewModel = basePlayerViewModel,
                        startNotificationService = startNotificationService,
                        onMenuClick = {
                            menuViewModel.setMenuFrom(K.MAIN)
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                        },
                        mainViewModel = mainViewModel,
                        menuViewModel = menuViewModel,
                        onSortByClick = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.SortAudioBy)
                        },
                        parentUiState = parentUiState
                    )

                    K.FOLDERS -> {
                        FoldersRootScreen(
                            folderList = mainViewModel.foldersList.collectAsStateWithLifecycle().value,
                            mainViewModel = mainViewModel,
                            onSortByClick = {
                                parentUiState.value = ParentUiState(ParentBottomSheetContent.SortFolderBy)
                            },
                            navController = navController,
                            folderMenuViewModel = folderMenuViewModel,
                            parentUiState = parentUiState
                        )
                    }
                }
            }
        }
    }
}