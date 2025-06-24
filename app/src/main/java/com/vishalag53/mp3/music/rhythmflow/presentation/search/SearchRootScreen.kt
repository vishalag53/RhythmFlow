package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.core.CenteredText
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.components.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchField
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchResult
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchViewModel

@Composable
fun SearchRootScreen(
    navController: NavHostController, navigateBack: () -> Boolean, mainViewModel: MainViewModel
) {
    val searchUiStateSaver: Saver<SearchUiState, *> = Saver(save = {
        listOf(
            it.isExpandedSongs,
            it.isExpandedPlaylists,
            it.isExpandedFolders,
            it.isExpandedArtists,
            it.isExpandedAlbums
        )
    }, restore = {
        SearchUiState(
            isExpandedSongs = it[0],
            isExpandedPlaylists = it[1],
            isExpandedFolders = it[2],
            isExpandedArtists = it[3],
            isExpandedAlbums = it[4]
        )
    })
    val searchUiState = rememberSaveable(stateSaver = searchUiStateSaver) {
        mutableStateOf(SearchUiState())
    }
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val audioList = mainViewModel.audioList.collectAsStateWithLifecycle().value
    var searchText by rememberSaveable { mutableStateOf("") }


    LaunchedEffect(audioList) {
        searchViewModel.setAudioList(audioList)
        if (searchText.isNotBlank()) {
            searchViewModel.searchQuery(searchText)
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
                    if (songs.isEmpty()) {
                        item {
                            CenteredText("Not Found Any Song")
                        }
                    } else {
                        items(songs) { audio ->
                            AudioItem(
                                audio = audio,
                                audioList = emptyList(),
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
                        }
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
                    if (true) {
                        item {
                            CenteredText("Not Found Any Playlist")
                        }
                    }
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
                    if (albums.isEmpty()) {
                        item {
                            CenteredText("Not Found Any Album")
                        }
                    } else {
                        items(albums) { album ->
                            Text(
                                text = album, color = MaterialTheme.colorScheme.primary
                            )
                        }
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
                    if (artists.isEmpty()) {
                        item {
                            CenteredText("Not Found Any Artist")
                        }
                    } else {
                        items(artists) { artist ->
                            Text(
                                text = artist, color = MaterialTheme.colorScheme.primary
                            )
                        }
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
                    if (folders.isEmpty()) {
                        item {
                            CenteredText("Not Found Any Folder")
                        }
                    } else {
                        items(folders) { folder ->
                            Text(
                                text = folder, color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}