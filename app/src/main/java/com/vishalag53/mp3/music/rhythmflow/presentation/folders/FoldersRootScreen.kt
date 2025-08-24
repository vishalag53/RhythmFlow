package com.vishalag53.mp3.music.rhythmflow.presentation.folders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu.FolderMenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel

@Composable
fun FoldersRootScreen(
    folderList: List<FolderData>,
    mainViewModel: MainViewModel,
    onSortByClick: () -> Unit,
    navController: NavHostController,
    folderMenuViewModel: FolderMenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel
) {
    Scaffold(
        topBar = {
            FoldersTopAppBar(
                size = folderList.size,
                refreshFolderList = { mainViewModel.refreshAudioList() },
                onSortByClick = onSortByClick
            )
        },
        contentWindowInsets = WindowInsets(0,0,0,0)
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
                items(folderList) { folder ->
                    FolderItem(
                        folder = folder,
                        navController = navController,
                        folderMenuViewModel = folderMenuViewModel,
                        parentUiState = parentUiState
                    )
                }
            }
        }
    }
}