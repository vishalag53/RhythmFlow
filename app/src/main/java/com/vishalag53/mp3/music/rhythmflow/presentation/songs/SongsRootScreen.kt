package com.vishalag53.mp3.music.rhythmflow.presentation.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.SelectedTopAppBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.components.SongsTopBar
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Build
import androidx.annotation.RequiresApi
import android.widget.Toast
import com.vishalag53.mp3.music.rhythmflow.domain.core.requestDeletePermissionForList
import com.vishalag53.mp3.music.rhythmflow.domain.core.deleteAudioFile
import com.vishalag53.mp3.music.rhythmflow.presentation.core.delete.Delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsRootScreen(
    audioList: List<Audio>,
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    onMenuClick: () -> Unit,
    menuViewModel: MenuViewModel,
    onSortByClick: () -> Unit,
    mainViewModel: MainViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel
) {
    val context = LocalContext.current
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val selectedItems = remember { mutableStateOf(setOf<Int>()) }
    val isSelectedItemEmpty = selectedItems.value.isEmpty()
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
                SongsTopBar(
                    audioList = audioList,
                    refreshAudioList = { mainViewModel.refreshAudioList() },
                    onSortByClick = onSortByClick,
                    parentUiState = parentUiState,
                    scrollBehavior = scrollBehavior,
                    navController = navController,
                    basePlayerViewModel = basePlayerViewModel,
                    startNotificationService = startNotificationService,
                    onMenuClick = onMenuClick,
                    menuViewModel = menuViewModel,
                    parentViewModel = parentViewModel,
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
                        onMenuClick = onMenuClick,
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
                        isSelected = isSelected,
                        onLongClick = {
                            selectedItems.value = if (isSelected) {
                                selectedItems.value - index
                            } else {
                                selectedItems.value + index
                            }
                        },
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