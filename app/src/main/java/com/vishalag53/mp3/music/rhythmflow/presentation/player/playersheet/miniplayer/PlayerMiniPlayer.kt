package com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.miniplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioProgressBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.miniplayer.components.MiniPlayerControllers

@Composable
internal fun PlayerMiniPlayer(audio: Audio) {
    Card(
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF35363B))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AudioProgressBar(Color(0xFF736659), audio)
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AudioTitleDisplayName(
                        title = stringCapitalized(audio.title),
                        display = stringCapitalized(audio.displayName),
                        color = Color(0xFFFDCF9E),
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        modifierColumn = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    MiniPlayerControllers()
                }
            }
        }
    }
}