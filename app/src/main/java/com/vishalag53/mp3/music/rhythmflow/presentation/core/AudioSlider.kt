package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun AudioSlider(inactiveColor: Color, progress: Float, onProgressChange: (Float) -> Unit) {
    Slider(
        value = progress,
        onValueChange = {
            onProgressChange(it)
        },
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFFFDCF9E),
            activeTrackColor = Color(0xFFFDCF9E),
            inactiveTrackColor = inactiveColor
        )
    )
}