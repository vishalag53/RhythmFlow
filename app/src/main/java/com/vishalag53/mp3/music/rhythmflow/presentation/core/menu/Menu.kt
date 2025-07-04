package com.vishalag53.mp3.music.rhythmflow.presentation.core.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName

@Composable
fun Menu(
    audio: Audio,
    onInfoClick: () -> Unit,
    onRepeatClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFDCF9E)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                AudioTitleDisplayName(
                    title = stringCapitalized(audio.title),
                    display = stringCapitalized(audio.displayName),
                    color = Color(0xFF35363B),
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifierColumn = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    tint = Color(0xFF35363B),
                    modifier = Modifier.clickable {
                        onInfoClick()
                    })

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_favourite_off),
                    contentDescription = null,
                    tint = Color(0xFF35363B)
                )
            }

            HorizontalDivider(
                thickness = 0.2.dp, color = Color.Gray
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(4.dp)
            ) {
                MenuComponent(
                    painter = R.drawable.ic_equalizer,
                    color = Color(0xFF35363B),
                    text = "Equalizer",
                    textColor = Color(0xFF35363B),
                    onClick = {}
                )

                MenuComponent(
                    painter = R.drawable.ic_repeat,
                    color = Color(0xFF35363B),
                    text = "Repeat",
                    textColor = Color(0xFF35363B),
                    onClick = onRepeatClick
                )

                MenuComponent(
                    painter = R.drawable.ic_shuffle,
                    color = Color(0xFF35363B),
                    text = "Shuffle",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_volume,
                    color = Color(0xFF35363B),
                    text = "Volume",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_add_to_playlist,
                    color = Color(0xFF35363B),
                    text = "Add to playlist",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_share,
                    color = Color(0xFF35363B),
                    text = "Share",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_ringtone,
                    color = Color(0xFF35363B),
                    text = "Use As a Ringtone",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_bluetooth,
                    color = Color(0xFF35363B),
                    text = "Bluetooth",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_sleep_timer,
                    color = Color(0xFF35363B),
                    text = "Sleep Timer",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_drive_mode,
                    color = Color(0xFF35363B),
                    text = "Drive Mode",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_round_lyrics,
                    color = Color(0xFF35363B),
                    text = "Lyrics",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                MenuComponent(
                    painter = R.drawable.ic_go_to,
                    color = Color(0xFF35363B),
                    text = "Go To",
                    textColor = Color(0xFF35363B),
                    onClick = {}
                )

                MenuComponent(
                    painter = R.drawable.ic_edit_audio,
                    color = Color(0xFF35363B),
                    text = "Edit audio",
                    textColor = Color(0xFF35363B),
                    onClick = { }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}