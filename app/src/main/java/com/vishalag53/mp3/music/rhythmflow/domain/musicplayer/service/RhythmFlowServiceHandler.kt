package com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RhythmFlowServiceHandler @Inject constructor(
    private val exoPlayer: ExoPlayer,
) : Player.Listener {
    private val _audioState = MutableStateFlow<AudioState>(AudioState.Initial)
    val audioState = _audioState.asStateFlow()

    private var job: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun setMediaItemList(mediaItems: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    fun onAudioEvents(
        audioEvent: AudioEvent,
        selectedAudioIndex: Int = -1,
        seekPosition: Long = 0
    ) {
        when (audioEvent) {
            AudioEvent.Backward -> exoPlayer.seekBack()
            AudioEvent.Forward -> exoPlayer.seekForward()
            is AudioEvent.PlayBackSpeed -> exoPlayer.setPlaybackSpeed(audioEvent.playbackSpeed)
            AudioEvent.PlayPause -> playOrPause()
            AudioEvent.SeekTo -> exoPlayer.seekTo(seekPosition)
            AudioEvent.SeekToNextItem -> {
                val hasNext = exoPlayer.hasNextMediaItem()
                if (hasNext) {
                    exoPlayer.seekToNextMediaItem()
                    exoPlayer.prepare()
                    _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
                }
            }
            AudioEvent.SeekToPreviousItem -> {
                val hasPrevious = exoPlayer.hasPreviousMediaItem()
                if (hasPrevious) {
                    exoPlayer.seekToPreviousMediaItem()
                    exoPlayer.prepare()
                    _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
                }
            }
            AudioEvent.SelectAudioChange -> {
                when (selectedAudioIndex) {
                    exoPlayer.currentMediaItemIndex -> playOrPause()

                    else -> {
                        exoPlayer.seekToDefaultPosition(selectedAudioIndex)
                        _audioState.value = AudioState.Playing(isPlaying = true)
                        exoPlayer.playWhenReady = true
                        startProgressUpdate()
                    }
                }
            }

            AudioEvent.Stop -> stopProgressUpdate()
            is AudioEvent.UpdateProgress -> exoPlayer.seekTo((exoPlayer.duration * audioEvent.newProgress).toLong())
            AudioEvent.ClearMediaItems -> exoPlayer.clearMediaItems()
            is AudioEvent.SetVolume -> exoPlayer.volume = audioEvent.volume
            is AudioEvent.SetRepeatMode -> exoPlayer.repeatMode = audioEvent.repeatMode
            is AudioEvent.SetShuffle -> exoPlayer.shuffleModeEnabled = audioEvent.shuffleModeEnabled
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _audioState.value =
                AudioState.Buffering(exoPlayer.currentPosition)

            ExoPlayer.STATE_READY -> _audioState.value = AudioState.Ready(exoPlayer.duration)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _audioState.value = AudioState.Playing(isPlaying = isPlaying)
        _audioState.value = AudioState.CurrentPlaying(exoPlayer.currentMediaItemIndex)
        if (isPlaying) {
            startProgressUpdate()
        } else {
            stopProgressUpdate()
        }
    }

    private fun playOrPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            stopProgressUpdate()
        } else {
            exoPlayer.play()
            _audioState.value = AudioState.Playing(isPlaying = true)
            startProgressUpdate()
        }
    }

    private fun startProgressUpdate() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(500L)
                _audioState.value = AudioState.Progress(exoPlayer.currentPosition)
            }
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _audioState.value = AudioState.Playing(isPlaying = false)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        val currentIndex = exoPlayer.currentMediaItemIndex
        _audioState.value = AudioState.CurrentPlaying(currentIndex)
    }
}