package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchAppBar
import kotlinx.coroutines.delay

@Composable
fun SearchRootScreen(
    navController: NavHostController,
    navigateBack: () -> Boolean,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    searchViewModel: SearchViewModel
) {
    val audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value
    var searchText by rememberSaveable { mutableStateOf("") }

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
                    parentUiState.value = ParentUiState(ParentBottomSheetContent.SearchTabSelector)
                },
                selectTabName = tabDisplayName
            )
        },
        bottomBar = {
            if (basePlayerViewModel.currentSelectedAudio.collectAsState().value.title != "") {
                SmallPlayerRootScreen(
                    audio = basePlayerViewModel.currentSelectedAudio.collectAsState().value,
                    progress = basePlayerViewModel.progress.collectAsState().value,
                    progressString = basePlayerViewModel.progressString.collectAsState().value,
                    isAudioPlaying = basePlayerViewModel.isPlaying.collectAsState().value,
                    onStart = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.PlayPause) },
                    onNext = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToNextItem) },
                    onPrev = { basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToPreviousItem) },
                    audioList = basePlayerViewModel.audioList.collectAsState().value,
                    index = basePlayerViewModel.currentSelectedAudioIndex.collectAsState().value + 1,
                    onClick = {
                        parentUiState.value = ParentUiState(ParentBottomSheetContent.PlayingQueue)
                    }
                ) {
                    navController.navigate(Screens.Player)
                }
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
                                index = index,
                                startNotificationService = startNotificationService,
                                basePlayerViewModel = basePlayerViewModel,
                                onMenuClick = {
                                    menuViewModel.setMenuFrom(K.SEARCH)
                                    parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
                                },
                                menuViewModel = menuViewModel
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
}