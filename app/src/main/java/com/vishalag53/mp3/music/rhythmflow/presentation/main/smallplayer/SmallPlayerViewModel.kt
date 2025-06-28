package com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer

import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmallPlayerViewModel @Inject constructor(
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

    private val _smallPlayerState: MutableStateFlow<SmallPlayerState> =
        MutableStateFlow(SmallPlayerState.Initial)
    val smallPlayerState = _smallPlayerState.asStateFlow()

    init {
        viewModelScope.launch {
            rhythmFlowServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    AudioState.Initial -> _smallPlayerState.value = SmallPlayerState.Initial
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
                        _currentSelectedAudio.value = _audioList.value[mediaState.mediaItemIndex]
                        _currentSelectedAudioIndex.value = mediaState.mediaItemIndex
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
                        _smallPlayerState.value = SmallPlayerState.Ready
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
            MediaItem.Builder().setUri(audio.uri).setMediaMetadata(
                MediaMetadata.Builder().setAlbumArtist(audio.artist)
                    .setDisplayTitle(audio.title).setSubtitle(audio.displayName).build()
            ).build()
        }.also {
            rhythmFlowServiceHandler.setMediaItemList(it)
        }
    }

    fun onSmallPlayerEvents(smallPlayerEvents: SmallPlayerEvents) = viewModelScope.launch {
        when (smallPlayerEvents) {
            SmallPlayerEvents.PlayPause -> rhythmFlowServiceHandler.onAudioEvents(PlayPause)
            SmallPlayerEvents.SeekToNextItem -> rhythmFlowServiceHandler.onAudioEvents(
                SeekToNextItem
            )

            SmallPlayerEvents.SeekToPreviousItem -> rhythmFlowServiceHandler.onAudioEvents(
                SeekToPreviousItem
            )

            is SmallPlayerEvents.SelectedAudioChange -> {
                rhythmFlowServiceHandler.onAudioEvents(
                    SelectAudioChange, selectedAudioIndex = smallPlayerEvents.index
                )
                _currentSelectedAudio.value = _audioList.value[smallPlayerEvents.index]
                _currentSelectedAudioIndex.value = smallPlayerEvents.index
            }

            is SmallPlayerEvents.UpdateProgress -> {
                rhythmFlowServiceHandler.onAudioEvents(
                    UpdateProgress(smallPlayerEvents.newProgress),
                )
                _progress.value = smallPlayerEvents.newProgress
            }

            SmallPlayerEvents.ClearMediaItems -> rhythmFlowServiceHandler.onAudioEvents(
                ClearMediaItems
            )
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            rhythmFlowServiceHandler.onAudioEvents(Stop)
        }
        super.onCleared()
    }
}