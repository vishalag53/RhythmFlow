package com.vishalag53.mp3.music.rhythmflow.presentation.core.menu

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    private val _repeatMode = MutableStateFlow("Repeat Off")
    val repeatMode = _repeatMode.asStateFlow()

    private val _shuffleText = MutableStateFlow("Shuffle Off")
    val shuffleText = _shuffleText.asStateFlow()

    fun setRepeatMode(repeat: String) {
        _repeatMode.value = repeat
    }

    fun setShuffle(shuffle: String) {
        _shuffleText.value = shuffle
    }
}