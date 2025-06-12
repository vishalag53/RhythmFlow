package com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer

import android.content.Context
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.asLongState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.calculateProgressValue
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.AudioState
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.PlayerEvent
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.RhythmFlowServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private val dummyAudio = Audio(
    uriString = "",
    id = "",
    displayName = "",
    title = "",
    album = "",
    dateAdded = 0L,
    dateModified = 0L,
    artist = "",
    duration = 0L,
    path = "",
    folderName = "",
    size = 0L,
    bitrate = 0L
)

@HiltViewModel
class SmallPlayerViewModel @Inject constructor(
    private val rhythmFlowServiceHandler: RhythmFlowServiceHandler,
    @ApplicationContext context: Context
) : ViewModel() {
    private val _duration = mutableLongStateOf(0L)
    val duration = _duration.asLongState()

    private val _progress = mutableFloatStateOf(0F)
    val progress = _progress.asFloatState()

    private val _isPlaying = mutableStateOf(false)
    val isPlaying = _isPlaying

    private val _progressString = mutableStateOf("00:00")
    val progressString = _progressString

    private val _currentSelectedAudio = mutableStateOf(dummyAudio)
    val currentSelectedAudio = _currentSelectedAudio

    private val _audioList = mutableStateOf(emptyList<Audio>())
    val audioList = _audioList

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
                            _duration.longValue
                        )
                        _progress.floatValue = newProgress
                        _progressString.value = newProgressString
                    }

                    is AudioState.Playing -> _isPlaying.value = mediaState.isPlaying
                    is AudioState.Progress -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
                            context,
                            _duration.longValue
                        )
                        _progress.floatValue = newProgress
                        _progressString.value = newProgressString
                    }

                    is AudioState.CurrentPlaying -> {
                        _currentSelectedAudio.value = _audioList.value[mediaState.mediaItemIndex]
                    }

                    is AudioState.Ready -> {
                        _duration.longValue = mediaState.duration
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
            MediaItem.Builder()
                .setUri(audio.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setAlbumArtist(audio.artist)
                        .setDisplayTitle(audio.title)
                        .setSubtitle(audio.displayName)
                        .build()
                )
                .build()
        }.also {
            rhythmFlowServiceHandler.setMediaItemList(it)
        }
    }

    fun onSmallPlayerEvents(smallPlayerEvents: SmallPlayerEvents) = viewModelScope.launch {
        when (smallPlayerEvents) {
            SmallPlayerEvents.Backward -> rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.Backward)
            SmallPlayerEvents.Forward -> rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.Forward)
            SmallPlayerEvents.SeekToNextItem -> rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.SeekToNextItem)
            SmallPlayerEvents.SeekToPreviousItem -> rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.SeekToPreviousItem)
            is SmallPlayerEvents.PlayPause -> rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)
            is SmallPlayerEvents.SeekTo -> rhythmFlowServiceHandler.onPlayerEvents(
                PlayerEvent.SeekTo,
                seekPosition = ((duration.value * smallPlayerEvents.position) / 100f).toLong()
            )

            is SmallPlayerEvents.SelectedAudioChange -> {
                rhythmFlowServiceHandler.onPlayerEvents(
                    PlayerEvent.SelectedAudioChange,
                    selectedAudioIndex = smallPlayerEvents.index
                )
            }

            is SmallPlayerEvents.UpdateProgress -> {
                rhythmFlowServiceHandler.onPlayerEvents(
                    PlayerEvent.UpdateProgress(smallPlayerEvents.newProgress)
                )
                _progress.floatValue = smallPlayerEvents.newProgress
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            rhythmFlowServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onCleared()
    }
}