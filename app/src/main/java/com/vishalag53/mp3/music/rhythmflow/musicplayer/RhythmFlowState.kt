package com.vishalag53.mp3.music.rhythmflow.musicplayer

sealed class RhythmFlowState {
    object Initial : RhythmFlowState()
    data class Ready(val duration: Long) : RhythmFlowState()
    data class Progress(val progress: Long) : RhythmFlowState()
    data class Buffering(val progress: Long) : RhythmFlowState()
    data class Playing(val isPlaying: Boolean) : RhythmFlowState()
    data class CurrentPlaying(val mediaItemIndex: Int) : RhythmFlowState()
}