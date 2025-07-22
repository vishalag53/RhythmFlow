package com.vishalag53.mp3.music.rhythmflow.presentation.search

import androidx.lifecycle.ViewModel
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _audioList = MutableStateFlow<List<Audio>>(emptyList())
    val audioList = _audioList.asStateFlow()

    private val _searchSongList = MutableStateFlow<List<Audio>>(emptyList())
    val searchSongList = _searchSongList.asStateFlow()

    private val _searchFolderList = MutableStateFlow<List<String>>(emptyList())
    val searchFolderList = _searchFolderList.asStateFlow()

    private val _searchPlaylistList = MutableStateFlow<List<String>>(emptyList())
    val searchPlaylistList = _searchPlaylistList.asStateFlow()

    private val _selectTabName = MutableStateFlow(K.SONGS)
    val selectTabName = _selectTabName.asStateFlow()

    fun setAudioList(audioList: List<Audio>) {
        _audioList.value = audioList
    }

    fun setSelectTabName(name: String) {
        _selectTabName.value = name
    }

    fun searchQuery(query: String) {
        if (query.isNotBlank()) {
            val lowerQuery = query.lowercase()

            _searchSongList.value = _audioList.value.filter {
                it.title.lowercase().contains(lowerQuery) || it.displayName.lowercase()
                    .contains(lowerQuery)
            }

            _searchFolderList.value = _audioList.value.mapNotNull {
                it.folderName.takeIf { folder ->
                    folder != "<unknown>" && folder.lowercase().contains(lowerQuery)
                }
            }.distinct()
        } else {
            _searchSongList.value = emptyList()
            _searchFolderList.value = emptyList()
        }
    }
}