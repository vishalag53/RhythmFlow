package com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed.components.PlaybackSpeedItem
import com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed.components.PlaybackSpeedTopBar

@Composable
fun PlaybackSpeedRootScreen(
    navController: NavHostController
) {
    val generalList = listOf(K.SONGS, K.FAVOURITE)

    Scaffold(
        topBar = {
            PlaybackSpeedTopBar(
                onBackClick = { navController.popBackStack() },
                onResetClick = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                generalList.forEach { item ->
                    PlaybackSpeedItem(
                        name = item
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = K.FOLDERS + " (0)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.W400,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = K.PLAYLISTS + " (0)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.W400,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}