package com.vishalag53.mp3.music.rhythmflow.presentation.parent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.navigation.RootNavigation
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.Menu
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playbackspeed.PlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.repeat.Repeat
import com.vishalag53.mp3.music.rhythmflow.presentation.core.songinfo.SongInfoRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.core.sortby.SortBy
import com.vishalag53.mp3.music.rhythmflow.presentation.main.components.SelectTabMainModalBottomSheet
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SelectTabSearchModalBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Parent(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    startNotificationService: () -> Unit,
    searchViewModel: SearchViewModel,
    parentViewModel: ParentViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // modal bottom sheet
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

    // Search tab selector
    val selectTabName = searchViewModel.selectTabName.collectAsStateWithLifecycle().value
    val songs = searchViewModel.searchSongList.collectAsStateWithLifecycle().value
    val albums = searchViewModel.searchAlbumList.collectAsStateWithLifecycle().value
    val artists = searchViewModel.searchArtistList.collectAsStateWithLifecycle().value
    val folders = searchViewModel.searchFolderList.collectAsStateWithLifecycle().value
    val playlists = searchViewModel.searchPlaylistList.collectAsStateWithLifecycle().value


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
                        })
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
                    SongInfoRootScreen(audio = menuViewModel.audio.collectAsStateWithLifecycle().value)
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
                        backgroundColor = when (parentViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.background
                            K.PLAYER -> Color(0xFF736659)
                            else -> MaterialTheme.colorScheme.background
                        },
                        backgroundIconColor = when (parentViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.secondary
                            K.PLAYER -> Color(0xFF35363B)
                            else -> MaterialTheme.colorScheme.secondary
                        },
                        iconColor = when (parentViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.primary
                            K.PLAYER -> Color(0xFFFDCF9E)
                            else -> MaterialTheme.colorScheme.primary
                        },
                        textColor = when (parentViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> MaterialTheme.colorScheme.primary
                            K.PLAYER -> Color(0xFF35363B)
                            else -> MaterialTheme.colorScheme.primary
                        },
                        isSongMenu =  when (parentViewModel.from.collectAsStateWithLifecycle().value) {
                            K.MAIN -> true
                            K.PLAYER -> false
                            else -> true
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

            ParentBottomSheetContent.SearchTabSelector -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value =
                        MaterialTheme.colorScheme.primaryContainer
                    SelectTabSearchModalBottomSheet(
                        songsSize = songs.size,
                        albumSize = albums.size,
                        artistSize = artists.size,
                        folderSize = folders.size,
                        playlistSize = playlists.size,
                        onClick = {
                            parentUiState.value = ParentUiState()
                        },
                        onSelectClick = { selected ->
                            searchViewModel.setSelectTabName(selected)
                        },
                        selectTabName = selectTabName
                    )
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.SelectTabName -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value =
                        MaterialTheme.colorScheme.primaryContainer
                    SelectTabMainModalBottomSheet(
                        mainViewModel = mainViewModel, onClose = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        })
                }
                scope.launch { sheetState.show() }
            }

            ParentBottomSheetContent.SortBy -> {
                showSheet.value = true
                sheetContent.value = {
                    modalBottomSheetBackgroundColor.value =
                        MaterialTheme.colorScheme.primaryContainer
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
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.None)
                        })
                }
                scope.launch { sheetState.show() }
            }
        }
    }

    RootNavigation(
        navController = navController,
        mainViewModel = mainViewModel,
        basePlayerViewModel = basePlayerViewModel,
        menuViewModel = menuViewModel,
        startNotificationService = { startNotificationService() },
        parentUiState = parentUiState,
        searchViewModel = searchViewModel,
        parentViewModel = parentViewModel
    )

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                parentUiState.value = ParentUiState()
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
}