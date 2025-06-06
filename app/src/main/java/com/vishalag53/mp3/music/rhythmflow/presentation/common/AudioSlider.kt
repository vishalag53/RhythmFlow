package com.vishalag53.mp3.music.rhythmflow.presentation.common

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
internal fun AudioSlider(inactiveColor: Color) {
    var sliderPosition by remember { mutableFloatStateOf(0.5f) }

    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFFFDCF9E),
            activeTrackColor = Color(0xFFFDCF9E),
            inactiveTrackColor = inactiveColor
        )
    )
}