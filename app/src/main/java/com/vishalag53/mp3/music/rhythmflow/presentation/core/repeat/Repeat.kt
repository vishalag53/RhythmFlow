package com.vishalag53.mp3.music.rhythmflow.presentation.core.repeat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player

@Composable
fun Repeat(
    repeatMode: String,
    onClose: () -> Unit,
    onRepeatChange: (String) -> Unit,
    onRepeatChangeBasePlayer: (Int) -> Unit
) {
    val repeatModeList = listOf(
        "Repeat Off",
        "Repeat One",
        "Repeat All"
    )

    val repeatModeIndex = listOf(
        Player.REPEAT_MODE_OFF,
        Player.REPEAT_MODE_ONE,
        Player.REPEAT_MODE_ALL
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        for (repeat in repeatModeList) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .clickable {
                        onRepeatChange(repeat)
                        onRepeatChangeBasePlayer(repeatModeIndex[repeatModeList.indexOf(repeat)])
                        onClose()
                    }
            ) {
                if (repeatMode == repeat) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF35363B)
                    )
                } else {
                    Spacer(modifier = Modifier.width(24.dp))
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = repeat,
                    color = Color(0xFF35363B),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}