package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration

@Composable
fun AudioProgressBar(
    inactiveColor: Color,
    audioDuration: Long,
    progress: Float,
    progressString: String,
    onProgressChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AudioSlider(
            inactiveColor = inactiveColor,
            progress = progress,
            onProgressChange = onProgressChange,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = progressString,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFFFDCF9E),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )

            Text(
                text = formatDuration(audioDuration),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFFFDCF9E),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
        }
    }
}