package com.vishalag53.mp3.music.rhythmflow.screen.player.playermodalsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
fun PlayerSmall() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF35363B))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            MusicProgressBar(Color(0xFF736659))

            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MusicTitleDisplayName(
                    title = stringCapitalized("Title"),
                    display = stringCapitalized("Display"),
                    color = Color(0xFFFDCF9E),
                    softWrap = false,
                    overflow = TextOverflow.Visible,
                    modifierText = Modifier.basicMarquee()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MusicPreviousPlay(
                        painter = painterResource(R.drawable.ic_player_previous_music),
                        tint = Color(0xFFFDCF9E),
                        modifier = Modifier
                            .width(18.dp)
                            .height(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    MusicPlayPause(
                        painter = painterResource(R.drawable.ic_music_play),
                        tint = Color(0xFFFDCF9E),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    MusicNextPlay(
                        painter = painterResource(R.drawable.ic_player_next_music),
                        tint = Color(0xFFFDCF9E),
                        modifier = Modifier
                            .width(18.dp)
                            .height(22.dp)
                    )
                }
            }
        }
    }
}