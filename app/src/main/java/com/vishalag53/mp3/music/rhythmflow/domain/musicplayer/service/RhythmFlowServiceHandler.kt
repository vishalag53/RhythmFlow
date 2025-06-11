package com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service

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
    private val exoPlayer: ExoPlayer
) : Player.Listener {
    private val _audioState: MutableStateFlow<AudioState> =
        MutableStateFlow(AudioState.Initial)
    val audioState = _audioState.asStateFlow()

    private var job: Job? = null

    init {
        exoPlayer.addListener(this)
    }

    fun setMediaItemList(mediaItems: List<MediaItem>) {
        exoPlayer.setMediaItems(mediaItems)
        exoPlayer.prepare()
    }

    fun onPlayerEvents(
        playerEvent: PlayerEvent,
        selectedAudioIndex: Int = -1,
        seekPosition: Long = 0,
        playbackSpeed: Float = 1.0f
    ) {
        when (playerEvent) {
            PlayerEvent.Backward -> exoPlayer.seekBack()
            PlayerEvent.Forward -> exoPlayer.seekForward()
            PlayerEvent.SeekToNextItem -> exoPlayer.seekToNextMediaItem()
            PlayerEvent.SeekToPreviousItem -> exoPlayer.seekToPreviousMediaItem()
            PlayerEvent.PlayPause -> playOrPause()
            PlayerEvent.SeekTo -> exoPlayer.seekTo(seekPosition)
            PlayerEvent.Stop -> stopProgressUpdate()
            PlayerEvent.SelectedAudioChange -> {
                when (selectedAudioIndex) {
                    exoPlayer.currentMediaItemIndex -> {
                        playOrPause()
                    }

                    else -> {
                        exoPlayer.seekToDefaultPosition(selectedAudioIndex)
                        _audioState.value = AudioState.Playing(isPlaying = true)
                        exoPlayer.playWhenReady = true
                        startProgressUpdate()
                    }
                }
            }
            is PlayerEvent.UpdateProgress -> exoPlayer.seekTo((exoPlayer.duration * playerEvent.newProgress).toLong())
            is PlayerEvent.PlayBackSpeed -> exoPlayer.setPlaybackSpeed(playbackSpeed)
        }
    }

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
}