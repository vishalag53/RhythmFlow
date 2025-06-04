package com.vishalag53.mp3.music.rhythmflow.screen.smallplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicNextPlay
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPlayPause
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicPreviousPlay
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicQueue
import com.vishalag53.mp3.music.rhythmflow.screen.smallplayer.components.SongPosition
import com.vishalag53.mp3.music.rhythmflow.screen.smallplayer.components.SongProgressBar
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTime
import com.vishalag53.mp3.music.rhythmflow.screen.common.MusicTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.stringCapitalized

@Composable
fun SmallPlayerRootScreen(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background, Color(0xFF3078AB)
                    )
                )
            )
            .clickable {
//                navController.navigate(Screens.Player())
            }
    ) {
        SongProgressBar(0.5f)

        Column(
            modifier = Modifier.padding(2.dp)
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MusicTime("00:00", MaterialTheme.colorScheme.primary, 12.sp)
                MusicTime("10:00", MaterialTheme.colorScheme.primary, 12.sp)
            }

            Spacer(modifier = Modifier.height(3.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                MusicTitleDisplayName(
                    title = stringCapitalized("Title Name"),
                    display = stringCapitalized("Display Name"),
                    color = MaterialTheme.colorScheme.primary,
                    softWrap = false,
                    overflow = TextOverflow.Visible,
                    modifierColumn = Modifier
                        .padding(start = 2.dp)
                        .weight(1f),
                    modifierText = Modifier.basicMarquee()
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MusicPreviousPlay(
                            painterResource(R.drawable.ic_play_previous),
                            MaterialTheme.colorScheme.primary,
                            Modifier
                                .width(17.dp)
                                .height(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MusicPlayPause(
                            painterResource(R.drawable.ic_music_play),
                            MaterialTheme.colorScheme.primary,
                            Modifier.size(46.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MusicNextPlay(
                            painterResource(R.drawable.ic_play_next),
                            MaterialTheme.colorScheme.primary,
                            Modifier
                                .width(17.dp)
                                .height(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MusicQueue(
                            MaterialTheme.colorScheme.primary, Modifier.size(24.dp)
                        )
                        SongPosition("1/100")
                    }
                }
            }
        }
    }
}