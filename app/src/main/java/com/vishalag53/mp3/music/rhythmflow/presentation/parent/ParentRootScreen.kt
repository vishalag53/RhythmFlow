package com.vishalag53.mp3.music.rhythmflow.presentation.parent

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
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.deleteAudioFile
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatBitrate
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDate
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatSize
import com.vishalag53.mp3.music.rhythmflow.domain.core.renameDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestDeletePermission
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestDeletePermissionForList
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestRenamePermission
import com.vishalag53.mp3.music.rhythmflow.navigation.RootNavigation
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Loading
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Delete
import com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu.FolderMenu
import com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu.FolderMenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.info.Info
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.Menu
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed.components.PlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Rename
import com.vishalag53.mp3.music.rhythmflow.presentation.core.repeat.Repeat
import com.vishalag53.mp3.music.rhythmflow.presentation.core.sortBy.SortBy
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ParentRootScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current

    // Viewmodel
    val basePlayerViewModel = hiltViewModel<BasePlayerViewModel>()
    val menuViewModel = hiltViewModel<MenuViewModel>()
    val folderMenuViewModel = hiltViewModel<FolderMenuViewModel>()
    val parentViewModel = hiltViewModel<ParentViewModel>()

    // Modal bottom sheet
    val parentUiStateSaver: Saver<ParentUiState, *> = Saver(save = {
        listOf(it.sheet::class.simpleName ?: "")
    }, restore = {
        val sheet = when (it.firstOrNull()) {
            ParentBottomSheetContent.PlaybackSpeed::class.simpleName -> ParentBottomSheetContent.PlaybackSpeed
            else -> ParentBottomSheetContent.None
        }
        ParentUiState(sheet = sheet)
    })

    val parentUiState = rememberSaveable(stateSaver = parentUiStateSaver) {
        mutableStateOf(ParentUiState())
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val scope = rememberCoroutineScope()
    val modalBottomSheetBackgroundColor = remember { mutableStateOf(Color(0xFF736659)) }

    // playback speed
    val playbackSpeed = remember { mutableFloatStateOf(1.0F) }

    // Delete
    val pendingDelete = parentViewModel.pendingDelete.collectAsStateWithLifecycle().value
    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingDelete?.let { audio ->
                deleteAudioFile(audio, context)
                mainViewModel.refreshAudioList()
                Toast.makeText(context, "Delete ${audio.displayName}", Toast.LENGTH_SHORT).show()
                parentViewModel.clearDelete()
            }
        }
    }

    val pendingMultiDeleteList = parentViewModel.pendingMultiDeleteList.collectAsStateWithLifecycle().value
    val multiDeleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (pendingMultiDeleteList.isNotEmpty()) {
                pendingMultiDeleteList.forEach { audio -> deleteAudioFile(audio, context) }
                mainViewModel.refreshAudioList()
                Toast.makeText(context, "Deleted ${pendingMultiDeleteList.size} items", Toast.LENGTH_SHORT).show()
                parentViewModel.clearMultiDelete()
            }
        }
    }

    // Rename
    val pendingRenameText = parentViewModel.pendingRename.collectAsStateWithLifecycle().value
    val pendingRename = remember { mutableStateOf<Pair<String, Audio>?>(null) }
    val renameLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingRename.value?.let { (newName, audio) ->
                renameDisplayName(newName, audio, context)
                mainViewModel.refreshAudioList()
                Toast.makeText(context, "Renamed to $newName", Toast.LENGTH_SHORT).show()
                pendingRename.value = null
                parentViewModel.clearRename()
            }
        }
    }

    // Sort Audio
    val sortAudioList = listOf(
        "Song title",
        "File name",
        "Song duration",
        "File size",
        "Folder name",
        "Album name",
        "Artist name",
        "Date added",
        "Date modified"
    )

    // Sort Folder
    val sortFolderList = listOf(
        "Folder name",
        "Song count",
        "Folder size",
        "Total songs time"
    )

    // Song Info
    val songInfo = parentViewModel.songInfo.collectAsStateWithLifecycle().value
    val aboutSong = listOf(
        "Song Name",
        "Display Name",
        "Artist",
        "Album",
        "Duration",
        "Size",
        "Bit Rate",
        "Date Added",
        "Date Modified",
        "Folder Name",
        "File Path"
    )
    val aboutSongInfo = if (songInfo != null) {
        listOf(
            songInfo.title,
            songInfo.displayName,
            songInfo.artist,
            songInfo.album,
            formatDuration(songInfo.duration),
            formatSize(songInfo.size),
            formatBitrate(songInfo.bitrate),
            formatDate(songInfo.dateAdded),
            formatDate(songInfo.dateModified),
            songInfo.folderName,
            songInfo.path
        )
    } else {
        listOf("")
    }

    // Folder Info
    val folder = folderMenuViewModel.folder.collectAsStateWithLifecycle().value
    val aboutFolder = listOf(
        "Name",
        "Total songs",
        "Total songs time",
        "Folder size",
        "Folder Path"
    )
    val aboutFolderInfo = listOf(
        folder.name,
        folder.length.toString(),
        formatDuration(folder.totalTime),
        formatSize(folder.totalSize),
        folder.path
    )

    LaunchedEffect(parentUiState.value.sheet) {
        when (parentUiState.value.sheet) {
            ParentBottomSheetContent.None -> {
                showSheet.value = false
                scope.launch { sheetState.hide() }
            }

            ParentBottomSheetContent.PlaybackSpeed -> {
                showSheet.value = true
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                sheetContent.value = {
                    PlaybackSpeed(
                        onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        },
                        playbackSpeed = playbackSpeed.floatValue,
                        onPlaybackSpeedChange = { speed ->
                            playbackSpeed.floatValue = speed
                        },
                        onPlaybackSpeedChangeBasePlayer = { speed ->
                            basePlayerViewModel.onBasePlayerEvents(
                                BasePlayerEvents.PlayBackSpeed(
                                    speed
                                )
                            )
                        },
                        from = parentViewModel.fromPlaybackSpeed.collectAsStateWithLifecycle().value
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.PlayingQueue -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    SongQueueListsItem(
                        audioList = basePlayerViewModel.audioList.collectAsStateWithLifecycle().value
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.SongInfo -> {
                modalBottomSheetBackgroundColor.value = Color(0xFF736659)
                showSheet.value = true
                sheetContent.value = {
                    Info(
                        about = aboutSong,
                        aboutInfo = aboutSongInfo
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.FolderInfo -> {
                modalBottomSheetBackgroundColor.value = Color(0xFF736659)
                showSheet.value = true
                sheetContent.value = {
                    Info(
                        about = aboutFolder,
                        aboutInfo = aboutFolderInfo
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.Menu -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    Menu(
                        onInfoClick = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.SongInfo)
                        },
                        onRepeatClick = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.Repeat)
                        },
                        onShuffleClick = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SetShuffle(it))
                        },
                        onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        },
                        menuViewModel = menuViewModel,
                        backgroundColor = when (menuViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.background
                            K.PLAYER -> Color(0xFF736659)
                            K.SEARCH -> MaterialTheme.colorScheme.background
                            else -> MaterialTheme.colorScheme.background
                        },
                        backgroundIconColor = when (menuViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.secondary
                            K.SEARCH -> MaterialTheme.colorScheme.secondary
                            K.PLAYER -> Color(0xFF35363B)
                            else -> MaterialTheme.colorScheme.secondary
                        },
                        iconColor = when (menuViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.primary
                            K.SEARCH -> MaterialTheme.colorScheme.primary
                            K.PLAYER -> Color(0xFFFDCF9E)
                            else -> MaterialTheme.colorScheme.primary
                        },
                        textColor = when (menuViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.primary
                            K.SEARCH -> MaterialTheme.colorScheme.primary
                            K.PLAYER -> Color(0xFF35363B)
                            else -> MaterialTheme.colorScheme.primary
                        },
                        isSongMenu = when (menuViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> true
                            K.SEARCH -> true
                            K.PLAYER -> false
                            else -> true
                        },
                        parentViewModel = parentViewModel
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.FolderMenu -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    FolderMenu(
                        folderMenuViewModel = folderMenuViewModel,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        backgroundIconColor = MaterialTheme.colorScheme.secondary,
                        iconColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.primary,
                        onInfoClick = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.FolderInfo)
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.Repeat -> {
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                showSheet.value = true
                sheetContent.value = {
                    Repeat(
                        repeatMode = menuViewModel.repeatMode.collectAsStateWithLifecycle().value,
                        onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        },
                        onRepeatChange = {
                            menuViewModel.setRepeatMode(it)
                        },
                        onRepeatChangeBasePlayer = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SetRepeatMode(it))
                        })
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.SortAudioBy -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value =
                        MaterialTheme.colorScheme.primaryContainer
                    SortBy(
                        sortList = sortAudioList,
                        sortBy = mainViewModel.sortAudioBy.value,
                        isAsc = mainViewModel.isAscAudio.value,
                        onSortByChange = {
                            mainViewModel.setSortAudioBy(it)
                            mainViewModel.sortAudioListBy()
                        },
                        onAscDescChange = {
                            mainViewModel.setIsAscAudio(it)
                            mainViewModel.sortAudioListBy()
                        },
                        onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        })
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.SortFolderBy -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value =
                        MaterialTheme.colorScheme.primaryContainer
                    SortBy(
                        sortList = sortFolderList,
                        sortBy = mainViewModel.sortFolderBy.value,
                        isAsc = mainViewModel.isAscFolder.value,
                        onSortByChange = {
                            mainViewModel.setSortFolderBy(it)
                            mainViewModel.sortFolderListBy()
                        },
                        onAscDescChange = {
                            mainViewModel.setIsAscFolder(it)
                            mainViewModel.sortFolderListBy()
                        },
                        onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RootNavigation(
                navController = navController,
                mainViewModel = mainViewModel,
                basePlayerViewModel = basePlayerViewModel,
                menuViewModel = menuViewModel,
                startNotificationService = { startNotificationService() },
                parentUiState = parentUiState,
                folderMenuViewModel = folderMenuViewModel,
                parentViewModel = parentViewModel
            )
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                parentUiState.value = ParentUiState()
                parentViewModel.clearSongInfo()
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
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = screenHeight * 0.7F)
                    .background(modalBottomSheetBackgroundColor.value)
            ) {
                sheetContent.value()
            }
        }
    }

    if (parentViewModel.showDeleteDialog.collectAsStateWithLifecycle().value && pendingDelete != null) {
        parentUiState.value = ParentUiState()

        Delete(
            displayName = pendingDelete.displayName,
            onConfirmDelete = {
                requestDeletePermission(
                    audio = pendingDelete,
                    context = context,
                    onPermissionGranted = { intentSender ->
                        deleteLauncher.launch(
                            IntentSenderRequest.Builder(intentSender).build()
                        )
                    },
                    onDeleteSuccess = {
                        deleteAudioFile(audio = pendingDelete, context = context)
                        mainViewModel.refreshAudioList()
                        Toast.makeText(context, "Delete ${pendingDelete.displayName}", Toast.LENGTH_SHORT).show()
                        parentViewModel.clearDelete()
                    }
                )
            },
            onDismiss = {
                parentViewModel.clearDelete()
            }
        )
    }

    if (parentViewModel.showMultiDeleteDialog.collectAsStateWithLifecycle().value && pendingMultiDeleteList.isNotEmpty()) {
        Delete(
            displayName = pendingMultiDeleteList.first().displayName,
            onConfirmDelete = {
                requestDeletePermissionForList(
                    audioList = pendingMultiDeleteList,
                    context = context,
                    onPermissionGranted = { intentSender ->
                        multiDeleteLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                    },
                    onDeleteSuccess = {
                        pendingMultiDeleteList.forEach { audio -> deleteAudioFile(audio, context) }
                        mainViewModel.refreshAudioList()
                        Toast.makeText(context, "Deleted ${pendingMultiDeleteList.size} items", Toast.LENGTH_SHORT).show()
                        parentViewModel.clearMultiDelete()
                    }
                )
            },
            onDismiss = {
                parentViewModel.clearMultiDelete()
            },
            count = pendingMultiDeleteList.size
        )
    }

    if (parentViewModel.showRenameDialog.collectAsStateWithLifecycle().value && pendingRenameText != null) {
        parentUiState.value = ParentUiState()

        val currentNameWithExtension = pendingRenameText.displayName
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
                parentViewModel.clearRename()
            },
            onRename = { editDisplayName ->
                val newDisplayName =
                    editDisplayName.trim() + if (extension.isNotEmpty()) ".$extension" else ""

                requestRenamePermission(
                    newDisplayName = newDisplayName,
                    audio = pendingRenameText,
                    context = context,
                    onPermissionGranted = { intentSender ->
                        pendingRename.value = newDisplayName to pendingRenameText
                        renameLauncher.launch(
                            IntentSenderRequest.Builder(intentSender).build()
                        )
                    },
                    onRenameSuccess = {
                        renameDisplayName(newDisplayName, pendingRenameText, context)
                        mainViewModel.refreshAudioList()
                        Toast.makeText(context, "Renamed to $newDisplayName", Toast.LENGTH_SHORT).show()
                        parentViewModel.clearRename()
                    }
                )
            }
        )
    }

    if (mainViewModel.isRefresh.collectAsStateWithLifecycle().value) {
        Loading(backgroundColor = Color.Black.copy(alpha = 0.3F))
    }
}