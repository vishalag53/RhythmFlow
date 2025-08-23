package com.vishalag53.mp3.music.rhythmflow.presentation.folder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.totalAudioTime
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.SelectedTopAppBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.folder.components.FolderTopBar
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import android.widget.Toast
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestDeletePermission
import com.vishalag53.mp3.music.rhythmflow.domain.core.deleteAudioFile
import com.vishalag53.mp3.music.rhythmflow.presentation.core.delete.Delete
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestDeletePermissionForList

@OptIn(ExperimentalMaterial3Api::class)
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
    val context = LocalContext.current
    val selectedItems = remember { mutableStateOf(setOf<Int>()) }
    val isSelectedItemEmpty = selectedItems.value.isEmpty()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val audioList =
        mainViewModel.audioList.collectAsStateWithLifecycle().value.filter { it.folderName == folder.name }
    val showMultiDeleteDialog = remember { mutableStateOf(false) }
    val pendingDeleteList = remember { mutableStateOf<List<Audio>>(emptyList()) }

    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingDeleteList.value.forEach { audio ->
                deleteAudioFile(audio, context)
            }
            mainViewModel.removeAudios(pendingDeleteList.value)
            Toast.makeText(context, "Deleted ${pendingDeleteList.value.size} items", Toast.LENGTH_SHORT).show()
            pendingDeleteList.value = emptyList()
            selectedItems.value = emptySet()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (isSelectedItemEmpty) {
                FolderTopBar(
                    folderName = folder.name,
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                    subtitle = "(${audioList.size})(${totalAudioTime(audioList)})",
                    refreshAudioList = { mainViewModel.refreshAudioList() },
                    parentUiState = parentUiState,
                    parentViewModel = parentViewModel,
                    onSortByClick = {
                        parentUiState.value =
                            ParentUiState(ParentBottomSheetContent.SortAudioBy)
                    },
                    audioList = audioList,
                    basePlayerViewModel = basePlayerViewModel,
                    startNotificationService = startNotificationService,
                    onMenuClick = {
                        menuViewModel.setMenuFrom(K.MAIN)
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                    },
                    menuViewModel = menuViewModel,
                    onSelectAllClick = {
                        selectedItems.value = (0 until audioList.size).toSet()
                    }
                )
            } else {
                SelectedTopAppBar(
                    selectedSize = selectedItems.value.size,
                    onCancelClick = {
                        selectedItems.value = emptySet()
                    },
                    onShareClick = {},
                    onDeleteClick = {
                        val toDelete = selectedItems.value.sorted().map { index -> audioList[index] }
                        if (toDelete.isNotEmpty()) {
                            showMultiDeleteDialog.value = true
                            pendingDeleteList.value = toDelete
                        }
                    },
                    isAllSelected = selectedItems.value.size == audioList.size,
                    onSelectAllClick = {
                        selectedItems.value = (0 until audioList.size).toSet()
                    }
                )
            }
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
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
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
                    val isSelected = selectedItems.value.contains(index)

                    AudioItem(
                        audio = audio,
                        onMenuClick = {
                            menuViewModel.setMenuFrom(K.FOLDERS)
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                        },
                        onClick = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.ClearMediaItems)
                            basePlayerViewModel.setAudioList(audioList)
                            basePlayerViewModel.onBasePlayerEvents(
                                BasePlayerEvents.SelectedAudioChange(
                                    index
                                )
                            )
                            navController.navigate(Screens.Player)
                            startNotificationService()
                        },
                        onLongClick = {
                            selectedItems.value = if (isSelected) {
                                selectedItems.value - index
                            } else {
                                selectedItems.value + index
                            }
                        },
                        isSelected = isSelected,
                        setMenuAudio = {
                            menuViewModel.setAudio(audio)
                        },
                        isSelectedItemEmpty = isSelectedItemEmpty
                    )
                }
            }
        }
    }

    if (showMultiDeleteDialog.value && pendingDeleteList.value.isNotEmpty()) {
        Delete(
            displayName = pendingDeleteList.value.first().displayName,
            onConfirmDelete = {
                requestDeletePermissionForList(
                    audios = pendingDeleteList.value,
                    context = context,
                    onPermissionGranted = { intentSender ->
                        deleteLauncher.launch(
                            IntentSenderRequest.Builder(intentSender).build()
                        )
                    },
                    onDeleteSuccess = {
                        pendingDeleteList.value.forEach { audio -> deleteAudioFile(audio, context) }
                        mainViewModel.removeAudios(pendingDeleteList.value)
                        Toast.makeText(context, "Deleted ${pendingDeleteList.value.size} items", Toast.LENGTH_SHORT).show()
                        pendingDeleteList.value = emptyList()
                        selectedItems.value = emptySet()
                    }
                )
                showMultiDeleteDialog.value = false
            },
            onDismiss = {
                showMultiDeleteDialog.value = false
                pendingDeleteList.value = emptyList()
            },
            count = pendingDeleteList.value.size
        )
    }
}