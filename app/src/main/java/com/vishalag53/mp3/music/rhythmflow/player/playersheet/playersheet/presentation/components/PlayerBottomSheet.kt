package com.vishalag53.mp3.music.rhythmflow.player.playersheet.playersheet.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio

@Composable
fun PlayerBottomSheet(audio: Audio) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .background(Color(0xFF736659))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width((screenWidth * 0.2).dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFF35363B))
                )
            }

            PlayerTabRow(audio)
        }
    }
}