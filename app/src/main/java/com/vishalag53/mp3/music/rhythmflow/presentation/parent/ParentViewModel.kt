package com.vishalag53.mp3.music.rhythmflow.presentation.parent

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(): ViewModel() {
    private val _fromPlaybackSpeed = MutableStateFlow("")
    val fromPlaybackSpeed = _fromPlaybackSpeed.asStateFlow()

    fun setFromPlaybackSpeed(from : String) {
        _fromPlaybackSpeed.value = from
    }
}