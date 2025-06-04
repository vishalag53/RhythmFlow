package com.vishalag53.mp3.music.rhythmflow.musicplayer

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.vishalag53.mp3.music.rhythmflow.calculateProgressValue
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.data.repository.AudioRepository
import com.vishalag53.mp3.music.rhythmflow.musicplayer.service.RhythmFlowServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private val audioDummy = Audio(
    "".toUri(), "", "", "", "", 0L, 0L, "", 0L, "", "", 0L, 0L
)

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioServiceHandler: RhythmFlowServiceHandler,
    private val repository: AudioRepository,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableLongStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableFloatStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    var currentSelectedAudio by savedStateHandle.saveable { mutableStateOf(audioDummy) }
    var audioList by savedStateHandle.saveable { mutableStateOf(listOf<Audio>()) }

    private val _smallPlayerState: MutableStateFlow<UIState> =
        MutableStateFlow(UIState.Initial)
    val smallPlayerState: StateFlow<UIState> = _smallPlayerState.asStateFlow()

    init {
        loadAudioData()
    }

    init {
        viewModelScope.launch {
            audioServiceHandler.audioState.collectLatest { mediaState ->
                when (mediaState) {
                    RhythmFlowState.Initial -> _smallPlayerState.value = UIState.Initial
                    is RhythmFlowState.Buffering -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
                            duration,
                            context
                        )
                        progress = newProgress
                        progressString = newProgressString
                    }

                    is RhythmFlowState.Playing -> isPlaying = mediaState.isPlaying
                    is RhythmFlowState.Progress -> {
                        val (newProgress, newProgressString) = calculateProgressValue(
                            mediaState.progress,
                            duration,
                            context
                        )
                        progress = newProgress
                        progressString = newProgressString
                    }

                    is RhythmFlowState.CurrentPlaying -> {
                        currentSelectedAudio = audioList[mediaState.mediaItemIndex]
                    }

                    is RhythmFlowState.Ready -> {
                        duration = mediaState.duration
                        _smallPlayerState.value = UIState.Ready
                    }
                }
            }
        }
    }

    private fun loadAudioData() {
        viewModelScope.launch {
            val audio = repository.getAudioData()
            audioList = audio
            setMediaItems()
        }
    }

    private fun setMediaItems() {
        audioList.map { audio ->
            MediaItem.Builder().setUri(audio.uri).setMediaMetadata(
                MediaMetadata.Builder().setAlbumArtist(audio.artist).setDisplayTitle(audio.title)
                    .setSubtitle(audio.displayName).build()
            ).build()
        }.also {
            audioServiceHandler.setMediaItemList(it)
        }
    }

    fun onSmallPlayerEvent(smallPlayerEvents: UIEvents) = viewModelScope.launch {
        when (smallPlayerEvents) {
            UIEvents.Backward -> audioServiceHandler.onPlayerEvents(PlayerEvent.Backward)
            UIEvents.Forward -> audioServiceHandler.onPlayerEvents(PlayerEvent.Forward)
            UIEvents.SeekToNext -> audioServiceHandler.onPlayerEvents(PlayerEvent.SeekToNext)
            is UIEvents.PlayPause -> {
                audioServiceHandler.onPlayerEvents(PlayerEvent.PlayPause)
            }

            is UIEvents.SeekTo -> {
                audioServiceHandler.onPlayerEvents(
                    PlayerEvent.SeekTo,
                    seekPosition = ((duration * smallPlayerEvents.position) / 100f).toLong()
                )
            }

            is UIEvents.SelectedAudioChange -> {
                audioServiceHandler.onPlayerEvents(
                    PlayerEvent.SelectedAudioChange, selectedAudioIndex = smallPlayerEvents.index
                )
            }

            is UIEvents.UpdateProgress -> {
                audioServiceHandler.onPlayerEvents(
                    PlayerEvent.UpdateProgress(
                        smallPlayerEvents.newProgress
                    )
                )
                progress = smallPlayerEvents.newProgress
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            audioServiceHandler.onPlayerEvents(PlayerEvent.Stop)
        }
        super.onCleared()
    }
}