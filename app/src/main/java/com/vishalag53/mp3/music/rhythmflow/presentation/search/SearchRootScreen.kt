package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchAppBar
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SelectTabSearchModalBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRootScreen(
    navController: NavHostController,
    navigateBack: () -> Boolean,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel
) {
    val searchUiStateSaver: Saver<SearchUiState, *> = Saver(save = {
        listOf(
            it.sheet::class.simpleName ?: ""
        )
    }, restore = {
        val sheet = when (it.firstOrNull()) {
            SearchBottomSheetContent.PlayingQueue::class.simpleName -> SearchBottomSheetContent.PlayingQueue
            SearchBottomSheetContent.TabSelector::class.simpleName -> SearchBottomSheetContent.TabSelector
            else -> SearchBottomSheetContent.None
        }
        SearchUiState(sheet = sheet)
    })
    val searchUiState = rememberSaveable(stateSaver = searchUiStateSaver) {
        mutableStateOf(SearchUiState())
    }
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value
    var searchText by rememberSaveable { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val backgroundColor = remember { mutableStateOf(Color(0xFF736659)) }
    val scope = rememberCoroutineScope()

    val selectTabName = searchViewModel.selectTabName.collectAsStateWithLifecycle().value
    val songs = searchViewModel.searchSongList.collectAsStateWithLifecycle().value
    val albums = searchViewModel.searchAlbumList.collectAsStateWithLifecycle().value
    val artists = searchViewModel.searchArtistList.collectAsStateWithLifecycle().value
    val folders = searchViewModel.searchFolderList.collectAsStateWithLifecycle().value
    val playlists = searchViewModel.searchPlaylistList.collectAsStateWithLifecycle().value

    val tabDisplayName = when (selectTabName) {
        K.SONGS -> K.SONGS + " (${songs.size})"
        K.ALBUMS -> K.ALBUMS + " (${albums.size})"
        K.ARTISTS -> K.ARTISTS + " (${artists.size})"
        K.FOLDERS -> K.FOLDERS + " (${folders.size})"
        K.PLAYLISTS -> K.PLAYLISTS + " (${playlists.size})"
        else -> selectTabName
    }

    LaunchedEffect(audioList) {
        searchViewModel.setAudioList(audioList)
    }

    LaunchedEffect(searchText) {
        delay(300L)
        searchViewModel.searchQuery(searchText)
    }

    LaunchedEffect(searchUiState.value.sheet) {
        when (searchUiState.value.sheet) {
            SearchBottomSheetContent.PlayingQueue -> {
                showSheet.value = true
                backgroundColor.value = Color(0xFF736659)
                sheetContent.value = {
                    SongQueueListsItem(
                        mainViewModel = mainViewModel,
                        navController = navController
                    )
                }
                scope.launch { sheetState.show() }
            }

            SearchBottomSheetContent.TabSelector -> {
                showSheet.value = true
                sheetContent.value = {
                    backgroundColor.value = MaterialTheme.colorScheme.primaryContainer
                    SelectTabSearchModalBottomSheet(
                        songsSize = songs.size,
                        albumSize = albums.size,
                        artistSize = artists.size,
                        folderSize = folders.size,
                        playlistSize = playlists.size,
                        onClick = {
                            searchUiState.value = SearchUiState()
                        },
                        onSelectClick = { selected ->
                            searchViewModel.setSelectTabName(selected)
                        },
                        selectTabName = selectTabName
                    )
                }
                scope.launch { sheetState.show() }
            }

            SearchBottomSheetContent.None -> {
                showSheet.value = false
                scope.launch { sheetState.hide() }
            }
        }
    }

    Scaffold(
        topBar = {
            SearchAppBar(
                searchText = searchText,
                onSearchTextChange = {
                    searchText = it
                },
                navigateBack = navigateBack,
                searchResult = { query ->
                    searchViewModel.searchQuery(query)
                },
                onClick = {
                    searchUiState.value = SearchUiState(SearchBottomSheetContent.TabSelector)
                },
                selectTabName = tabDisplayName
            )
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
                        searchUiState.value = SearchUiState(SearchBottomSheetContent.PlayingQueue)
                    }
                )
            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
            ) {
                when (selectTabName) {
                    K.SONGS -> {
                        itemsIndexed(songs) { index, audio ->
                            AudioItem(
                                audio = audio,
                                audioList = songs,
                                navController = navController,
                                mainViewModel = mainViewModel,
                                smallPlayerViewModel = smallPlayerViewModel,
                                index = index
                            )
                        }
                    }

                    K.PLAYLISTS -> {}
                    K.ALBUMS -> {
                        items(albums) { album ->
                            Text(
                                text = album,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    K.ARTISTS -> {
                        items(artists) { artist ->
                            Text(
                                text = artist,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    K.FOLDERS -> {
                        items(folders) { folder ->
                            Text(
                                text = folder,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                searchUiState.value = SearchUiState()
                scope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            containerColor = backgroundColor.value,
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
                    .background(backgroundColor.value)
            ) {
                sheetContent.value()
            }
        }
    }
}