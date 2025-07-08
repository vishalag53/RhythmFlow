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
        sortAudioListByDisplayName()
    }

    fun sortAudioListByDisplayName() {
        _audioList.value = _audioList.value.sortedBy { it.displayName.lowercase() }
    }

    fun refreshAudioList() {
        viewModelScope.launch {
            _isRefresh.value = true
            _audioList.value = repository.getAudioData()
            delay(1000L)
            _isRefresh.value = false
        }
    }
}