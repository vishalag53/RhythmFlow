package com.vishalag53.mp3.music.rhythmflow.presentation.parent

import androidx.lifecycle.ViewModel
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(): ViewModel() {
    private val _from = MutableStateFlow(K.MAIN)
    val from = _from.asStateFlow()

    fun setMenuFrom(text: String) {
        _from.value = text
    }
}