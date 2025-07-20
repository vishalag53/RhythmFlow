package com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized

@Composable
fun SongQueueItem(
    audio: Audio
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_draggable),
                    contentDescription = null,
                    tint = Color(0xFF35363B),
                    modifier = Modifier.size(26.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                AudioTitleDisplayName(
                    title = stringCapitalized(audio.title),
                    display = stringCapitalized(audio.displayName),
                    color = Color(0xFF35363B),
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifierColumn = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDuration(audio.duration),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFF35363B),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_clear),
                    contentDescription = null,
                    tint = Color(0xFF35363B),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}