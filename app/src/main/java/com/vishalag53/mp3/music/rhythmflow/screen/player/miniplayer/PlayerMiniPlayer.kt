package com.vishalag53.mp3.music.rhythmflow.screen.player.miniplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicNextPlay
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPlayPause
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPreviousPlay
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicProgressBar
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.stringCapitalized

@Composable
internal fun PlayerMiniPlayer() {
    Card(
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.background(Color(0xFF35363B)).padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MusicProgressBar(Color(0xFF736659))

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MusicTitleDisplayName(
                        title = stringCapitalized("song.title"),
                        display = stringCapitalized("song.displayName"),
                        color = Color(0xFFFDCF9E),
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        modifierColumn = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Row(
                        modifier = Modifier.width(120.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MusicPreviousPlay(
                            painterResource(R.drawable.ic_player_previous_music),
                            Color(0xFFFDCF9E),
                            Modifier.size(24.dp)
                        )

                        MusicPlayPause(
                            painterResource(R.drawable.ic_music_play),
                            Color(0xFFFDCF9E),
                            Modifier.size(44.dp)
                        )

                        MusicNextPlay(
                            painterResource(R.drawable.ic_player_next_music),
                            Color(0xFFFDCF9E),
                            Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}