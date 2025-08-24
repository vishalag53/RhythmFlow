package com.vishalag53.mp3.music.rhythmflow.presentation.parent

import androidx.lifecycle.ViewModel
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(): ViewModel() {
    // Playback Speed
    private val _fromPlaybackSpeed = MutableStateFlow("")
    val fromPlaybackSpeed = _fromPlaybackSpeed.asStateFlow()

    fun setFromPlaybackSpeed(from : String) {
        _fromPlaybackSpeed.value = from
    }

    // Delete
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private val _pendingDelete = MutableStateFlow<Audio?>(null)
    val pendingDelete = _pendingDelete.asStateFlow()

    fun requestDelete(audio: Audio) {
        _pendingDelete.value = audio
        _showDeleteDialog.value = true
    }

    fun clearDelete() {
        _pendingDelete.value = null
        _showDeleteDialog.value = false
    }

    // Multi-delete
    private val _pendingMultiDeleteList = MutableStateFlow<List<Audio>>(emptyList())
    val pendingMultiDeleteList = _pendingMultiDeleteList.asStateFlow()

    private val _showMultiDeleteDialog = MutableStateFlow(false)
    val showMultiDeleteDialog = _showMultiDeleteDialog.asStateFlow()

    fun requestMultiDelete(audioList: List<Audio>) {
        _pendingMultiDeleteList.value = audioList
        _showMultiDeleteDialog.value = audioList.isNotEmpty()
    }

    fun clearMultiDelete() {
        _pendingMultiDeleteList.value = emptyList()
        _showMultiDeleteDialog.value = false
    }
}