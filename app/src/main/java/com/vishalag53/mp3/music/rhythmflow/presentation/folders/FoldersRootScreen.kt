package com.vishalag53.mp3.music.rhythmflow.presentation.folders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vishalag53.mp3.music.rhythmflow.domain.core.Folder
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel

@Composable
fun FoldersRootScreen(
    folderList: List<Folder>,
    mainViewModel: MainViewModel,
    onSortByClick: () -> Unit
) {
    Scaffold(
        topBar = {
            FolderTopAppBar(
                size = folderList.size,
                refreshFolderList = { mainViewModel.refreshAudioList() },
                onSortByClick = onSortByClick
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
                items(folderList) { folder ->
                    FolderItem(
                        folder = folder
                    )
                }
            }
        }
    }
}