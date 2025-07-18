package com.vishalag53.mp3.music.rhythmflow.presentation.parent

import androidx.lifecycle.ViewModel
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.domain.core.dummyAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(): ViewModel() {
    private val _from = MutableStateFlow(K.MAIN)
    val from = _from.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private val _selectAudio = MutableStateFlow(dummyAudio)
    val selectAudio = _selectAudio.asStateFlow()

    fun setMenuFrom(text: String) {
        _from.value = text
    }

    fun setShowDeleteDialog(isShow : Boolean) {
        _showDeleteDialog.value = isShow
    }

    fun setSelectAudio(audio: Audio) {
        _selectAudio.value = audio
    }
}