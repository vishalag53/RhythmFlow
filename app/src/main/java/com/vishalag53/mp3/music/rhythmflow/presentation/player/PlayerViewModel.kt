package com.vishalag53.mp3.music.rhythmflow.presentation.player

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.calculateProgressValue
import com.vishalag53.mp3.music.rhythmflow.domain.core.dummyAudio
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.AudioEvent
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.AudioState
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.RhythmFlowServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val rhythmFlowServiceHandler: RhythmFlowServiceHandler,
    @ApplicationContext context: Context
) : ViewModel() {
    private val _duration = MutableStateFlow(0L)
    val duration = _duration.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progressString = MutableStateFlow("00:00")
    val progressString = _progressString.asStateFlow()

    private val _currentSelectedAudio = MutableStateFlow(dummyAudio)
    val currentSelectedAudio = _currentSelectedAudio.asStateFlow()

    private val _audioList = MutableStateFlow<List<Audio>>(emptyList())
    val audioList = _audioList.asStateFlow()

    private val _currentSelectedAudioIndex = MutableStateFlow(0)
    val currentSelectedAudioIndex = _currentSelectedAudioIndex.asStateFlow()

    private val _playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState.Initial)
    val playerState = _playerState.asStateFlow()

    init {
        viewModelScope.launch {
            rhythmFlowServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    AudioState.Initial -> _playerState.value = PlayerState.Initial
                    is AudioState.Buffering -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
                            context,
                            _duration.value,
                        )
                        _progress.value = newProgress
                        _progressString.value = newProgressString
                    }

                    is AudioState.CurrentPlaying -> {
                        val index = mediaState.mediaItemIndex
                        val list = _audioList.value
                        if (index in list.indices) {
                            _currentSelectedAudio.value = list[index]
                            _currentSelectedAudioIndex.value = index
                        }
                    }

                    is AudioState.Playing -> _isPlaying.value = mediaState.isPlaying
                    is AudioState.Progress -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
                            context,
                            _duration.value,
                        )
                        _progress.value = newProgress
                        _progressString.value = newProgressString
                    }

                    is AudioState.Ready -> {
                        _duration.value = mediaState.duration
                        _playerState.value = PlayerState.Ready
                    }
                }
            }
        }
    }

    fun setAudioList(audioList: List<Audio>) {
        _audioList.value = audioList
    }

    fun onPlayerEvents(playerEvents: PlayerEvents) = viewModelScope.launch {
        when (playerEvents) {
            PlayerEvents.Backward -> rhythmFlowServiceHandler.onAudioEvents(AudioEvent.Backward)
            PlayerEvents.Forward -> rhythmFlowServiceHandler.onAudioEvents(AudioEvent.Forward)
            is PlayerEvents.PlayBackSpeed -> rhythmFlowServiceHandler.onAudioEvents(
                AudioEvent.PlayBackSpeed(
                    playerEvents.playbackSpeed
                )
            )

            PlayerEvents.PlayPause -> rhythmFlowServiceHandler.onAudioEvents(AudioEvent.PlayPause)
            PlayerEvents.SeekToNextItem -> rhythmFlowServiceHandler.onAudioEvents(AudioEvent.SeekToNextItem)
            PlayerEvents.SeekToPreviousItem -> rhythmFlowServiceHandler.onAudioEvents(AudioEvent.SeekToPreviousItem)
            is PlayerEvents.SelectedAudioChange -> {
                rhythmFlowServiceHandler.onAudioEvents(
                    AudioEvent.SelectAudioChange, selectedAudioIndex = playerEvents.index
                )
                _currentSelectedAudio.value = _audioList.value[playerEvents.index]
                _currentSelectedAudioIndex.value = playerEvents.index
            }

            is PlayerEvents.SetRepeatMode -> rhythmFlowServiceHandler.onAudioEvents(
                AudioEvent.SetRepeatMode(
                    playerEvents.repeatMode
                )
            )

            is PlayerEvents.SetShuffle -> rhythmFlowServiceHandler.onAudioEvents(
                AudioEvent.SetShuffle(
                    playerEvents.shuffleModeEnabled
                )
            )

            is PlayerEvents.SetVolume -> rhythmFlowServiceHandler.onAudioEvents(
                AudioEvent.SetVolume(
                    playerEvents.volume
                )
            )

            is PlayerEvents.UpdateProgress -> rhythmFlowServiceHandler.onAudioEvents(
                AudioEvent.UpdateProgress(
                    playerEvents.newProgress
                )
            )
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            rhythmFlowServiceHandler.onAudioEvents(AudioEvent.Stop)
        }
        super.onCleared()
    }
}