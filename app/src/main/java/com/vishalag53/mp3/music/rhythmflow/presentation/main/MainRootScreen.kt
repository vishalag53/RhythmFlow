package com.vishalag53.mp3.music.rhythmflow.presentation.main

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.renameDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestRenamePermission
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Loading
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.Menu
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.rename.Rename
import com.vishalag53.mp3.music.rhythmflow.presentation.core.repeat.Repeat
import com.vishalag53.mp3.music.rhythmflow.presentation.core.songinfo.SongInfoRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.core.sortby.SortBy
import com.vishalag53.mp3.music.rhythmflow.presentation.main.components.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.SongsRootScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainRootScreen(
    tab: String,
    navController: NavHostController,
    audioList: List<Audio>,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    updateDisplayName: (String, String) -> Unit,
    refreshAudioList: () -> Unit,
    isRefresh: Boolean,
    mainViewModel: MainViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val pendingRename = remember { mutableStateOf<Pair<String, Audio>?>(null) }

    val renameLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingRename.value?.let { (newName, audio) ->
                renameDisplayName(newName, audio, context)
                updateDisplayName(audio.id, newName)
                Toast.makeText(context, "Renamed to $newName", Toast.LENGTH_SHORT).show()
                pendingRename.value = null
            }
        }
    }

    val mainUiStateSaver: Saver<MainUiState, *> = Saver(save = {
        listOf(it.sheet::class.simpleName ?: "")
    }, restore = {
        val sheet = when (it.firstOrNull()) {
            MainBottomSheetContent.PlayingQueue::class.simpleName -> MainBottomSheetContent.PlayingQueue
            MainBottomSheetContent.SongInfo::class.simpleName -> MainBottomSheetContent.SongInfo
            MainBottomSheetContent.Menu::class.simpleName -> MainBottomSheetContent.Menu
            MainBottomSheetContent.Repeat::class.simpleName -> MainBottomSheetContent.Repeat
            MainBottomSheetContent.SortBy::class.simpleName -> MainBottomSheetContent.SortBy
            else -> MainBottomSheetContent.None
        }
        MainUiState(sheet = sheet)
    })
    val mainUiState = rememberSaveable(stateSaver = mainUiStateSaver) {
        mutableStateOf(MainUiState())
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val scope = rememberCoroutineScope()
    val modalBottomSheetBackgroundColor = remember { mutableStateOf(Color(0xFF736659)) }

    LaunchedEffect(mainUiState.value.sheet) {
        when (mainUiState.value.sheet) {
            MainBottomSheetContent.PlayingQueue -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    SongQueueListsItem(
                        audioList = basePlayerViewModel.audioList.collectAsStateWithLifecycle().value
                    )
                }
                scope.launch { sheetState.show() }
            }

            MainBottomSheetContent.SongInfo -> {
                modalBottomSheetBackgroundColor.value = Color(0xFF736659)
                showSheet.value = true
                sheetContent.value = {
                    SongInfoRootScreen(audio = menuViewModel.audio.collectAsStateWithLifecycle().value)
                }
                scope.launch { sheetState.show() }
            }

            MainBottomSheetContent.Menu -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    Menu(
                        onInfoClick = {
                            mainUiState.value = MainUiState(MainBottomSheetContent.SongInfo)
                        },
                        onRepeatClick = {
                            mainUiState.value = MainUiState(MainBottomSheetContent.Repeat)
                        },
                        onShuffleClick = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SetShuffle(it))
                        },
                        onClose = {
                            mainUiState.value = MainUiState(MainBottomSheetContent.None)
                        },
                        menuViewModel = menuViewModel,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        backgroundIconColor = MaterialTheme.colorScheme.secondary,
                        iconColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.primary,
                        isSongMenu = true
                    )
                }
                scope.launch { sheetState.show() }
            }

            MainBottomSheetContent.Repeat -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    Repeat(
                        repeatMode = menuViewModel.repeatMode.collectAsStateWithLifecycle().value,
                        onClose = {
                            mainUiState.value = MainUiState(MainBottomSheetContent.None)
                        },
                        onRepeatChange = {
                            menuViewModel.setRepeatMode(it)
                        },
                        onRepeatChangeBasePlayer = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SetRepeatMode(it))
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            MainBottomSheetContent.SortBy -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value = MaterialTheme.colorScheme.primaryContainer
                    SortBy(
                        sortBy = mainViewModel.sortBy.value,
                        isAsc = mainViewModel.isAsc.value,
                        onSortByChange = {
                            mainViewModel.setSortBy(it)
                            mainViewModel.sortAudioListBy()
                        },
                        onAscDescChange = {
                            mainViewModel.setIsAsc(it)
                            mainViewModel.sortAudioListBy()
                        },
                        onClose = {
                            mainUiState.value = MainUiState(MainBottomSheetContent.None)
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            MainBottomSheetContent.None -> {
                showSheet.value = false
                scope.launch { sheetState.hide() }
            }
        }
    }

    Scaffold(
        topBar = {
            AppBarRootScreen(navController = navController)
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
                        mainUiState.value = MainUiState(MainBottomSheetContent.PlayingQueue)
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
            when (tab) {
                K.SONGS -> SongsRootScreen(
                    navController = navController,
                    audioList = audioList,
                    basePlayerViewModel = basePlayerViewModel,
                    startNotificationService = startNotificationService,
                    onMenuClick = {
                        mainUiState.value = MainUiState(MainBottomSheetContent.Menu)
                    },
                    menuViewModel = menuViewModel,
                    refreshAudioList = refreshAudioList,
                    onSortByClick = {
                        mainUiState.value = MainUiState(MainBottomSheetContent.SortBy)
                    }
                )
            }

            if (menuViewModel.showRenameBox.collectAsStateWithLifecycle().value) {
                var currentNameWithExtension =
                    menuViewModel.audio.collectAsStateWithLifecycle().value.displayName

                val currentName = if (currentNameWithExtension.contains(".")) {
                    currentNameWithExtension.substringBeforeLast(".")
                } else {
                    currentNameWithExtension
                }

                var extension = if (currentNameWithExtension.contains(".")) {
                    currentNameWithExtension.substringAfterLast(".")
                } else {
                    ""
                }

                Rename(
                    currentNameWithExtension = currentNameWithExtension,
                    currentName = currentName,
                    onDismiss = {
                        menuViewModel.setRenameBox(false)
                    },
                    onRename = { editDisplayName ->
                        val newDisplayName =
                            editDisplayName.trim() + if (extension.isNotEmpty()) ".$extension" else ""
                        val audio = menuViewModel.audio.value

                        requestRenamePermission(
                            newDisplayName = newDisplayName,
                            audio = audio,
                            context = context,
                            onPermissionGranted = { intentSender ->
                                pendingRename.value = newDisplayName to audio
                                renameLauncher.launch(
                                    IntentSenderRequest.Builder(intentSender).build()
                                )
                            },
                            onRenameSuccess = {
                                renameDisplayName(newDisplayName, audio, context)
                                Toast.makeText(context, "Renamed to $newDisplayName", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    screenHeight = screenHeight / 4
                )
            }

            if (isRefresh) {
                Loading(backgroundColor = Color.Black.copy(alpha = 0.3F))
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                mainUiState.value = MainUiState()
                scope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            containerColor = modalBottomSheetBackgroundColor.value,
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
                    .heightIn(max = screenHeight * 0.6F)
                    .background(modalBottomSheetBackgroundColor.value)
            ) {
                sheetContent.value()
            }
        }
    }
}