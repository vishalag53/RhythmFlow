package com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.getAllPlaybackSpeeds
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.model.PlaybackSpeedModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaybackSpeedViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {
    private val _allPlaybackSpeed = MutableStateFlow<List<PlaybackSpeedModel>>(emptyList())
    val allPlaybackSpeed = _allPlaybackSpeed.asStateFlow()

    init {
        viewModelScope.launch {
            getAllPlaybackSpeeds(context).collect { list ->
                _allPlaybackSpeed.value = list
            }
        }
    }
}