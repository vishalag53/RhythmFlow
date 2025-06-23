package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.dummyAudio
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.components.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchHistory
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchHistoryItem
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchResult
import com.vishalag53.mp3.music.rhythmflow.presentation.search.components.SearchTopBar

@Composable
fun SearchRootScreen(
    navController: NavHostController,
    navigateBack: () -> Boolean,
    mainViewModel: MainViewModel
) {
    val searchUiState = remember { mutableStateOf(SearchUiState()) }

    Scaffold(
        topBar = {
            SearchTopBar(
                navigateBack = navigateBack,
                onHistoryStateChanged = { isHistory ->
                    searchUiState.value = searchUiState.value.copy(isHistory = isHistory)
                }
            )
        }
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
                if (searchUiState.value.isHistory) {
                    item {
                        SearchHistory()
                    }
                    items(1) {
                        SearchHistoryItem("Item $it")
                    }
                } else {
//                     Songs
                    item {
                        SearchResult(
                            resultText = K.SONGS,
                            searchUiState = searchUiState.value,
                            onExpandToggle = { isExpanded ->
                                searchUiState.value =
                                    searchUiState.value.copy(isExpandedSongs = isExpanded)
                            }
                        )
                    }
                    if (searchUiState.value.isExpandedSongs) {
                        items(1) {
                            AudioItem(
                                audio = dummyAudio,
                                audioList = emptyList(),
                                navController = navController,
                                mainViewModel = mainViewModel
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
                            }
                        )
                    }
                    if (searchUiState.value.isExpandedPlaylists) {
                        item {
                            Text(
                                text = "Play",
                                color = MaterialTheme.colorScheme.primary
                            )
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
                            }
                        )
                    }
                    if (searchUiState.value.isExpandedAlbums) {
                        item {
                            Text(
                                text = "Alb",
                                color = MaterialTheme.colorScheme.primary
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
                            }
                        )
                    }
                    if (searchUiState.value.isExpandedArtists) {
                        item {
                            Text(
                                text = "Art",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

//                    .
                    item {
                        SearchResult(
                            resultText = K.FOLDERS,
                            searchUiState = searchUiState.value,
                            onExpandToggle = { isExpanded ->
                                searchUiState.value =
                                    searchUiState.value.copy(isExpandedFolders = isExpanded)
                            }
                        )
                    }
                    if (searchUiState.value.isExpandedFolders) {
                        item {
                            Text(
                                text = "Fold",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}