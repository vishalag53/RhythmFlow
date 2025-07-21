package com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.totalAudioTime

@Composable
internal fun SongQueueListsItem(audioList: List<Audio>) {
    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left
            ) {
                Text(
                    text = "Playing Queue",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFF35363B),
                    maxLines = 1,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "(${audioList.size})(${totalAudioTime(audioList)})",
                    color = Color(0xBF35363B),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color(0xFF35363B)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = null,
                    tint = Color(0xFF35363B)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.5.dp)
                .background(Color(0x8035363B))
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 6.dp)
        ) {
            items(audioList) { audio ->
                SongQueueItem(
                    audio = audio
                )
            }
        }
    }
}