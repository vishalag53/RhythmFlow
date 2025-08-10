package com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.data.local.repository.AudioRepository
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AudioRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    private val _audioList = MutableStateFlow<List<Audio>>(emptyList())
    val audioList = _audioList.asStateFlow()

    private val _keepSplashOn = MutableStateFlow(true)
    val keepSplashOn get() = _keepSplashOn.asStateFlow()

    private val _sortAudioBy = MutableStateFlow("File name")
    val sortAudioBy = _sortAudioBy.asStateFlow()

    private val _isAscAudio = MutableStateFlow(true)
    val isAscAudio = _isAscAudio.asStateFlow()

    private val _foldersList = MutableStateFlow<List<FolderData>>(emptyList())
    val foldersList = _foldersList.asStateFlow()

    private val _sortFolderBy = MutableStateFlow("Folder name")
    val sortFolderBy = _sortFolderBy.asStateFlow()

    private val _isAscFolder = MutableStateFlow(true)
    val isAscFolder = _isAscFolder.asStateFlow()


    init {
        viewModelScope.launch {
            delay(2000)
            _keepSplashOn.value = false
        }
    }

    init {
        loadAudioData()
    }

    private fun loadAudioData() {
        viewModelScope.launch {
            _isLoading.value = true
            val audio = repository.getAudioData()
            _audioList.value = audio
            delay(300L)
            _isLoading.value = false
            getFolderList()
        }
    }

    private fun getFolderList() {
        val folders = mutableListOf<FolderData>()
        val folderList = _audioList.value
            .map { it.folderName to it.path }
            .distinctBy { it.first }

        for ((folderName, path) in folderList) {
            val audiosInFolder = _audioList.value.filter { it.folderName == folderName }
            val folder = FolderData(
                name = folderName,
                path = path,
                length = audiosInFolder.size,
                totalTime = audiosInFolder.sumOf { it.duration },
                totalSize = audiosInFolder.sumOf { it.size }
            )
            folders.add(folder)
        }
        _foldersList.value = folders
        sortFolderListBy()
    }

    fun updateDisplayName(audioId: String, newDisplayName: String) {
        _audioList.update { list ->
            list.map {
                if (it.id == audioId) it.copy(displayName = newDisplayName) else it
            }
        }
        sortAudioListBy()
    }

    fun removeAudio(audio: Audio) {
        _audioList.update { list ->
            list.filterNot { it.id == audio.id }
        }
        sortAudioListBy()
    }

    fun refreshAudioList() {
        viewModelScope.launch {
            _isRefresh.value = true
            _audioList.value = repository.getAudioData()
            delay(300L)
            sortAudioListBy()
            _isRefresh.value = false
            getFolderList()
        }
    }

    fun setSortAudioBy(sortBy: String) {
        _sortAudioBy.value = sortBy
    }

    fun setIsAscAudio(isASC: Boolean) {
        _isAscAudio.value = isASC
    }

    fun sortAudioListBy() {
        when (_sortAudioBy.value) {
            "Song title" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.title.lowercase() }
                    else _audioList.value.sortedByDescending { it.title.lowercase() }
            }

            "File name" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.displayName.lowercase() }
                    else _audioList.value.sortedByDescending { it.displayName.lowercase() }
            }

            "Song duration" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.duration }
                    else _audioList.value.sortedByDescending { it.duration }
            }

            "File size" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.size }
                    else _audioList.value.sortedByDescending { it.size }
            }

            "Folder name" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.folderName.lowercase() }
                    else _audioList.value.sortedByDescending { it.folderName.lowercase() }
            }

            "Album name" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.album.lowercase() }
                    else _audioList.value.sortedByDescending { it.album.lowercase() }
            }

            "Artist name" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.artist.lowercase() }
                    else _audioList.value.sortedByDescending { it.artist.lowercase() }
            }

            "Date added" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.dateAdded }
                    else _audioList.value.sortedByDescending { it.dateAdded }
            }

            "Date modified" -> {
                _audioList.value =
                    if (_isAscAudio.value) _audioList.value.sortedBy { it.dateModified }
                    else _audioList.value.sortedByDescending { it.dateModified }
            }
        }
    }

    fun setSortFolderBy(sortBy: String) {
        _sortFolderBy.value = sortBy
    }

    fun setIsAscFolder(isASC: Boolean) {
        _isAscFolder.value = isASC
    }

    fun sortFolderListBy() {
        when (_sortFolderBy.value) {
            "Folder name" -> {
                _foldersList.value =
                    if (_isAscFolder.value) _foldersList.value.sortedBy { it.name }
                    else _foldersList.value.sortedByDescending { it.name }
            }
            "Folder length" -> {
                _foldersList.value =
                    if (_isAscFolder.value) _foldersList.value.sortedBy { it.length }
                    else _foldersList.value.sortedByDescending { it.length }
            }
            "Folder size" -> {
                _foldersList.value =
                    if (_isAscFolder.value) _foldersList.value.sortedBy { it.totalSize }
                    else _foldersList.value.sortedByDescending { it.totalSize }
            }
            "Folder time" -> {
                _foldersList.value =
                    if (_isAscFolder.value) _foldersList.value.sortedBy { it.totalTime }
                    else _foldersList.value.sortedByDescending { it.totalTime }
            }
        }
    }
}