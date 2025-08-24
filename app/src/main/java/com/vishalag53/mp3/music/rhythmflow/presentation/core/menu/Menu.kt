package com.vishalag53.mp3.music.rhythmflow.presentation.core.menu

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.MenuItemData
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.presentation.core.MenuComponent
import com.vishalag53.mp3.music.rhythmflow.presentation.core.MenuRow
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel

@Composable
fun Menu(
    onInfoClick: () -> Unit,
    onRepeatClick: () -> Unit,
    onShuffleClick: (Boolean) -> Unit,
    onClose: () -> Unit,
    menuViewModel: MenuViewModel,
    backgroundColor: Color,
    backgroundIconColor: Color,
    iconColor: Color,
    textColor: Color,
    isSongMenu: Boolean,
    parentViewModel: ParentViewModel
) {
    val audio = menuViewModel.audio.collectAsStateWithLifecycle().value
    val width = (LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2

    val repeatText = menuViewModel.repeatMode.collectAsStateWithLifecycle().value
    val shuffleText = menuViewModel.shuffleText.collectAsStateWithLifecycle().value

    val onShuffleChange = {
        val newShuffle = shuffleText == "Shuffle Off"
        menuViewModel.setShuffle(if (newShuffle) "Shuffle On" else "Shuffle Off")
        onShuffleClick(newShuffle)
    }
    val onRenameClick = {
        menuViewModel.setRenameBox(true)
        onClose()
    }
    val onDeleteClick = {
        parentViewModel.requestDelete(audio)
    }

    val songMenuItems = listOf(
        listOf(
            MenuItemData(R.drawable.ic_rename, "Rename", onRenameClick),
            MenuItemData(R.drawable.ic_edit_audio_info, "Edit song info") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_trash, "Trash") {},
            MenuItemData(R.drawable.ic_delete, "Delete", onDeleteClick)
        ),
        listOf(
            MenuItemData(R.drawable.ic_hide, "Hide") {},
            MenuItemData(R.drawable.ic_round_lyrics, "Lyric") {}
        )
    )

    val generalMenuItems = listOf(
        listOf(
            MenuItemData(R.drawable.ic_add_to_playing_queue, "Add to playing queue") {},
            MenuItemData(R.drawable.ic_add_to_next_play, "Add to next play") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_equalizer, "Equalizer") {},
            MenuItemData(R.drawable.ic_repeat, repeatText, onRepeatClick)
        ),
        listOf(
            MenuItemData(R.drawable.ic_shuffle, shuffleText, onShuffleChange),
            MenuItemData(R.drawable.ic_bluetooth, "Bluetooth") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_add_to_playlist, "Add to playlist") {},
            MenuItemData(R.drawable.ic_share, "Share") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_ringtone, "Use as a ringtone") {},
            MenuItemData(R.drawable.ic_sleep_timer, "Sleep timer") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_drive_mode, "Drive mode") {},
            MenuItemData(R.drawable.ic_go_to, "Go to") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_volume, "Volume") {},
            MenuItemData(R.drawable.ic_edit_audio, "Edit audio") {}
        )
    )

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

                IconButton(
                    onClick = onInfoClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_info),
                        contentDescription = null,
                        tint = Color(0xFF35363B)
                    )
                }

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
                if (isSongMenu) {
                    songMenuItems.forEach { row ->
                        MenuRow(
                            width = width,
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            textColor = textColor,
                            items = row
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                generalMenuItems.forEach { row ->
                    MenuRow(
                        width = width,
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        items = row
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                if (!isSongMenu) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_round_lyrics,
                        text = "Lyric",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {}
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}