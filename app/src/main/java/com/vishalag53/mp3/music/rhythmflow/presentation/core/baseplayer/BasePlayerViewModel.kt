package com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.calculateProgressValue
import com.vishalag53.mp3.music.rhythmflow.domain.core.dummyAudio
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.AudioEvent.*
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.AudioState
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.RhythmFlowServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasePlayerViewModel @Inject constructor(
    private val rhythmFlowServiceHandler: RhythmFlowServiceHandler,
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

    private val _basePlayerState: MutableStateFlow<BasePlayerState> =
        MutableStateFlow(BasePlayerState.Initial)
    val basePlayerState = _basePlayerState.asStateFlow()

    init {
        viewModelScope.launch {
            rhythmFlowServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    AudioState.Initial -> _basePlayerState.value = BasePlayerState.Initial
                    is AudioState.Buffering -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
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
                            _duration.value,
                        )
                        _progress.value = newProgress
                        _progressString.value = newProgressString
                    }

                    is AudioState.Ready -> {
                        _duration.value = mediaState.duration
                        _basePlayerState.value = BasePlayerState.Ready
                    }
                }
            }
        }
    }

    fun setAudioList(audioList: List<Audio>) {
        _audioList.value = audioList
        setMediaItems()
    }

    private fun setMediaItems() {
        _audioList.value.map { audio ->
            MediaItem
                .Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata
                        .Builder()
                        .setAlbumArtist(audio.artist)
                        .setTitle(audio.title)
                        .setDisplayTitle(audio.displayName)
                        .build()
                ).build()
        }.also {
            rhythmFlowServiceHandler.setMediaItemList(it)
        }
    }

    fun onBasePlayerEvents(basePlayerEvents: BasePlayerEvents) = viewModelScope.launch {
        when (basePlayerEvents) {
            BasePlayerEvents.Backward -> rhythmFlowServiceHandler.onAudioEvents(Backward)
            BasePlayerEvents.Forward -> rhythmFlowServiceHandler.onAudioEvents(Forward)
            is BasePlayerEvents.PlayBackSpeed -> rhythmFlowServiceHandler.onAudioEvents(
                PlayBackSpeed(
                    basePlayerEvents.playbackSpeed
                )
            )

            BasePlayerEvents.PlayPause -> rhythmFlowServiceHandler.onAudioEvents(PlayPause)
            BasePlayerEvents.SeekToNextItem -> {
                resetProgress()
                rhythmFlowServiceHandler.onAudioEvents(SeekToNextItem)
            }

            BasePlayerEvents.SeekToPreviousItem -> {
                resetProgress()
                rhythmFlowServiceHandler.onAudioEvents(SeekToPreviousItem)
            }

            is BasePlayerEvents.SelectedAudioChange -> {
                _audioList.value.getOrNull(basePlayerEvents.index)?.let {
                    _currentSelectedAudio.value = it
                    _currentSelectedAudioIndex.value = basePlayerEvents.index
                    rhythmFlowServiceHandler.onAudioEvents(
                        SelectAudioChange, selectedAudioIndex = basePlayerEvents.index
                    )
                }
            }

            is BasePlayerEvents.SetRepeatMode -> rhythmFlowServiceHandler.onAudioEvents(
                SetRepeatMode(
                    basePlayerEvents.repeatMode
                )
            )

            is BasePlayerEvents.SetShuffle -> rhythmFlowServiceHandler.onAudioEvents(
                SetShuffle(
                    basePlayerEvents.shuffleModeEnabled
                )
            )

            is BasePlayerEvents.SetVolume -> rhythmFlowServiceHandler.onAudioEvents(
                SetVolume(
                    basePlayerEvents.volume
                )
            )

            is BasePlayerEvents.UpdateProgress -> {
                rhythmFlowServiceHandler.onAudioEvents(UpdateProgress(basePlayerEvents.newProgress))
            }

            BasePlayerEvents.ClearMediaItems -> rhythmFlowServiceHandler.onAudioEvents(
                ClearMediaItems
            )
        }
    }

    private fun resetProgress() {
        _progress.value = 0f
        _progressString.value = "00:00"
    }


    override fun onCleared() {
        viewModelScope.launch {
            rhythmFlowServiceHandler.onAudioEvents(Stop)
        }
        super.onCleared()
    }
}