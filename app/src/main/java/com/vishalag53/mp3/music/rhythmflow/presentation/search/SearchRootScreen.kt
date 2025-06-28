package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchField
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchResult
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchViewModel

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
            it.isExpandedSongs,
            it.isExpandedPlaylists,
            it.isExpandedFolders,
            it.isExpandedArtists,
            it.isExpandedAlbums,
            it.isPlayingQueue
        )
    }, restore = {
        SearchUiState(
            isExpandedSongs = it[0],
            isExpandedPlaylists = it[1],
            isExpandedFolders = it[2],
            isExpandedArtists = it[3],
            isExpandedAlbums = it[4],
            isPlayingQueue = it[5]
        )
    })
    val searchUiState = rememberSaveable(stateSaver = searchUiStateSaver) {
        mutableStateOf(SearchUiState())
    }
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value
    var searchText by rememberSaveable { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }

    LaunchedEffect(audioList) {
        searchViewModel.setAudioList(audioList)
        if (searchText.isNotBlank()) {
            searchViewModel.searchQuery(searchText)
        }
    }

    LaunchedEffect(searchUiState.value) {
        showSheet.value = searchUiState.value.isPlayingQueue
        sheetContent.value = when {
            searchUiState.value.isPlayingQueue -> {
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
            SearchField(
                searchText = searchText,
                onSearchTextChange = {
                    searchText = it
                },
                navigateBack = navigateBack,
                onSearchStateChanged = {
                    searchUiState.value = searchUiState.value.copy(
                        isExpandedSongs = false,
                        isExpandedAlbums = false,
                        isExpandedArtists = false,
                        isExpandedPlaylists = false,
                        isExpandedFolders = false
                    )
                },
                searchResult = { query ->
                    searchViewModel.searchQuery(query)
                })
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
                        searchUiState.value =
                            searchUiState.value.copy(isPlayingQueue = true)
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
                state = rememberLazyListState(), modifier = Modifier.fillMaxSize()
            ) {
//                     Songs
                item {
                    SearchResult(
                        resultText = K.SONGS,
                        searchUiState = searchUiState.value,
                        onExpandToggle = { isExpanded ->
                            searchUiState.value =
                                searchUiState.value.copy(isExpandedSongs = isExpanded)
                        },
                        size = searchViewModel.searchSongList.collectAsStateWithLifecycle().value.size
                    )
                }
                if (searchUiState.value.isExpandedSongs) {
                    val songs = searchViewModel.searchSongList.value
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

//                     Playlists
                item {
                    SearchResult(
                        resultText = K.PLAYLISTS,
                        searchUiState = searchUiState.value,
                        onExpandToggle = { isExpanded ->
                            searchUiState.value =
                                searchUiState.value.copy(isExpandedPlaylists = isExpanded)
                        },
                        size = 0
                    )
                }
                if (searchUiState.value.isExpandedPlaylists) {
                }

//                     Albums
                item {
                    SearchResult(
                        resultText = K.ALBUMS,
                        searchUiState = searchUiState.value,
                        onExpandToggle = { isExpanded ->
                            searchUiState.value =
                                searchUiState.value.copy(isExpandedAlbums = isExpanded)
                        },
                        size = searchViewModel.searchAlbumList.collectAsStateWithLifecycle().value.size
                    )
                }
                if (searchUiState.value.isExpandedAlbums) {
                    val albums = searchViewModel.searchAlbumList.value
                    items(albums) { album ->
                        Text(
                            text = album, color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

//                    ARTISTS
                item {
                    SearchResult(
                        resultText = K.ARTISTS,
                        searchUiState = searchUiState.value,
                        onExpandToggle = { isExpanded ->
                            searchUiState.value =
                                searchUiState.value.copy(isExpandedArtists = isExpanded)
                        },
                        size = searchViewModel.searchArtistList.collectAsStateWithLifecycle().value.size
                    )
                }
                if (searchUiState.value.isExpandedArtists) {
                    val artists = searchViewModel.searchArtistList.value
                    items(artists) { artist ->
                        Text(
                            text = artist, color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

//                    Folder
                item {
                    SearchResult(
                        resultText = K.FOLDERS,
                        searchUiState = searchUiState.value,
                        onExpandToggle = { isExpanded ->
                            searchUiState.value =
                                searchUiState.value.copy(isExpandedFolders = isExpanded)
                        },
                        size = searchViewModel.searchFolderList.collectAsStateWithLifecycle().value.size
                    )
                }
                if (searchUiState.value.isExpandedFolders) {
                    val folders = searchViewModel.searchFolderList.value
                    items(folders) { folder ->
                        Text(
                            text = folder, color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                searchUiState.value = SearchUiState()
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