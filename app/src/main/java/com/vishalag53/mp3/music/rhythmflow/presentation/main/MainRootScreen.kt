package com.vishalag53.mp3.music.rhythmflow.presentation.main

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.renameDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestRenamePermission
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Loading
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.rename.Rename
import com.vishalag53.mp3.music.rhythmflow.presentation.folders.FoldersRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.components.AppBarRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.SongsRootScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainRootScreen(
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    mainViewModel: MainViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel
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
                mainViewModel.updateDisplayName(audio.id, newName)
                Toast.makeText(context, "Renamed to $newName", Toast.LENGTH_SHORT).show()
                pendingRename.value = null
            }
        }
    }

    Scaffold(
        topBar = {
            AppBarRootScreen(
                navController = navController,
                mainViewModel = mainViewModel,
                onSelectTabMainClick  = {
                    parentUiState.value = ParentUiState(ParentBottomSheetContent.SelectTabName)
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
                    navController = navController,
                    basePlayerViewModel = basePlayerViewModel,
                    startNotificationService = startNotificationService,
                    onMenuClick = {
                        parentViewModel.setMenuFrom(K.MAIN)
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                    },
                    mainViewModel = mainViewModel,
                    menuViewModel = menuViewModel,
                    onSortByClick = {
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.SortBy)
                    },
                    parentUiState = parentUiState
                )

                K.FOLDERS -> {
                    FoldersRootScreen()
                }
            }

            if (menuViewModel.showRenameBox.collectAsStateWithLifecycle().value) {
                val currentNameWithExtension =
                    menuViewModel.audio.collectAsStateWithLifecycle().value.displayName

                val currentName = if (currentNameWithExtension.contains(".")) {
                    currentNameWithExtension.substringBeforeLast(".")
                } else {
                    currentNameWithExtension
                }

                val extension = if (currentNameWithExtension.contains(".")) {
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
                                Toast.makeText(
                                    context,
                                    "Renamed to $newDisplayName",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    screenHeight = screenHeight / 4
                )
            }

            if (mainViewModel.isRefresh.collectAsStateWithLifecycle().value) {
                Loading(backgroundColor = Color.Black.copy(alpha = 0.3F))
            }
        }
    }
}