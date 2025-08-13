package com.vishalag53.mp3.music.rhythmflow.presentation.songs.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarScrollBehavior
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.totalAudioTime
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.songs.songallmenu.SongAllMenu
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsTopBar(
    audioList: List<Audio>,
    refreshAudioList: () -> Unit,
    onSortByClick: () -> Unit,
    parentUiState: MutableState<ParentUiState>,
    scrollBehavior: SearchBarScrollBehavior,
    navController: NavHostController,
    basePlayerViewModel: BasePlayerViewModel,
    startNotificationService: () -> Unit,
    onMenuClick: () -> Unit,
    menuViewModel: MenuViewModel,
) {
    val scope = rememberCoroutineScope()
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()

    val searchAudioLists by remember(audioList, textFieldState.text) {
        derivedStateOf {
            val lowerQuery = textFieldState.text.toString().lowercase()
            if (lowerQuery.isBlank()) {
                emptyList()
            } else {
                audioList.filter {
                    it.title.lowercase().contains(lowerQuery) || it.displayName.lowercase().contains(lowerQuery)
                }
            }
        }
    }

    LaunchedEffect(searchBarState.currentValue) {
        if (searchBarState.currentValue == SearchBarValue.Collapsed) {
            textFieldState.edit {
                replace(0, length, "")
            }
        }
    }

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = {
                scope.launch {
                    searchBarState.animateToCollapsed()
                }
            },
            placeholder = {
                Text(
                    text = "Play all (${audioList.size})(${totalAudioTime(audioList)})"
                )
            },
            leadingIcon = {
                if (searchBarState.currentValue == SearchBarValue.Expanded) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                searchBarState.animateToCollapsed()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = {
                if (searchBarState.currentValue == SearchBarValue.Collapsed) {
                    SongAllMenu(
                        onSortByClick = onSortByClick,
                        refreshAudioList = refreshAudioList,
                        parentUiState = parentUiState
                    )
                } else if (textFieldState.text.toString().isNotEmpty()){
                    IconButton(
                        onClick = {
                            textFieldState.edit {
                                replace(0, length, "")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.05f),
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        )
    }

    TopSearchBar(
        state = searchBarState,
        scrollBehavior = scrollBehavior,
        inputField = inputField,
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = inputField,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(searchAudioLists) { index, audio ->
                AudioItem(
                    audio = audio,
                    audioList = searchAudioLists,
                    navController = navController,
                    basePlayerViewModel = basePlayerViewModel,
                    index = index,
                    startNotificationService = startNotificationService,
                    onMenuClick = onMenuClick,
                    menuViewModel = menuViewModel
                )
            }
        }
    }
}