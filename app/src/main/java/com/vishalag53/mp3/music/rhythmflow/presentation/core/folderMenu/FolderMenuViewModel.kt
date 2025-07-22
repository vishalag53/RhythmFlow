package com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu

import androidx.lifecycle.ViewModel
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.domain.core.dummyFolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FolderMenuViewModel @Inject constructor() : ViewModel() {
    private val _folder = MutableStateFlow(dummyFolder)
    val folder = _folder.asStateFlow()

    fun setFolder(folder: FolderData) {
        _folder.value = folder
    }
}