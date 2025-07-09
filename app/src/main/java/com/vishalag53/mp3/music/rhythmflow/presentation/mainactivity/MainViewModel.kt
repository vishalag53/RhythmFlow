package com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.data.local.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
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

    private val _sortBy = MutableStateFlow("File name")
    val sortBy = _sortBy.asStateFlow()

    private val _isAsc = MutableStateFlow(true)
    val isAsc = _isAsc.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _keepSplashOn.value = false
        }
    }

    fun loadAudioData() {
        viewModelScope.launch {
            _isLoading.value = true
            val audio = repository.getAudioData()
            _audioList.value = audio
            delay(1000L)
            _isLoading.value = false
        }
    }

    fun updateDisplayName(audioId: String, newDisplayName: String) {
        _audioList.update { list ->
            list.map {
                if (it.id == audioId) it.copy(displayName = newDisplayName) else it
            }
        }
        sortAudioListBy()
    }

    fun refreshAudioList() {
        viewModelScope.launch {
            _isRefresh.value = true
            _audioList.value = repository.getAudioData()
            delay(1000L)
            _isRefresh.value = false
        }
        sortAudioListBy()
    }

    fun setSortBy(sortBy: String) {
        _sortBy.value = sortBy
    }

    fun setIsAsc(isASC: Boolean) {
        _isAsc.value = isASC
    }

    fun sortAudioListBy() {
        when (_sortBy.value) {
            "Song title" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.title.lowercase() }
                    else _audioList.value.sortedByDescending { it.title.lowercase() }
            }

            "File name" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.displayName.lowercase() }
                    else _audioList.value.sortedByDescending { it.displayName.lowercase() }
            }

            "Song duration" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.duration }
                    else _audioList.value.sortedByDescending { it.duration }
            }

            "File size" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.size }
                    else _audioList.value.sortedByDescending { it.size }
            }

            "Folder name" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.folderName.lowercase() }
                    else _audioList.value.sortedByDescending { it.folderName.lowercase() }
            }

            "Album name" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.album.lowercase() }
                    else _audioList.value.sortedByDescending { it.album.lowercase() }
            }

            "Artist name" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.artist.lowercase() }
                    else _audioList.value.sortedByDescending { it.artist.lowercase() }
            }

            "Date added" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.dateAdded }
                    else _audioList.value.sortedByDescending { it.dateAdded }
            }

            "Date modified" -> {
                _audioList.value =
                    if (_isAsc.value) _audioList.value.sortedBy { it.dateModified }
                    else _audioList.value.sortedByDescending { it.dateModified }
            }
        }
    }
}