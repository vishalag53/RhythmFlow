package com.vishalag53.mp3.music.rhythmflow.presentation.core.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
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
                if (isSongMenu) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_rename,
                            text = "Rename",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            textColor = textColor,
                            onClick = {
                                menuViewModel.setRenameBox(true)
                                onClose()
                            }
                        )

                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_edit_audio_info,
                            text = "Edit song info",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            onClick = {},
                            textColor = textColor
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_trash,
                            text = "Trash",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            textColor = textColor,
                            onClick = {})

                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_delete,
                            text = "Delete",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            onClick = {
                                parentViewModel.setSelectAudio(audio)
                                parentViewModel.setShowDeleteDialog(true)
                            },
                            textColor = textColor
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_hide,
                            text = "Hide",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            textColor = textColor,
                            onClick = {})

                        MenuComponent(
                            width = width,
                            painter = R.drawable.ic_round_lyrics,
                            text = "Lyric",
                            backgroundColor = backgroundColor,
                            backgroundIconColor = backgroundIconColor,
                            iconColor = iconColor,
                            onClick = {},
                            textColor = textColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_add_to_playing_queue,
                        text = "Add to playing queue",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_add_to_next_play,
                        text = "Add to next play",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_equalizer,
                        text = "Equalizer",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_repeat,
                        text = menuViewModel.repeatMode.collectAsStateWithLifecycle().value,
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = onRepeatClick,
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_shuffle,
                        text = menuViewModel.shuffleText.collectAsStateWithLifecycle().value,
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {
                            if (menuViewModel.shuffleText.value == "Shuffle Off") {
                                menuViewModel.setShuffle("Shuffle On")
                                onShuffleClick(true)
                            } else {
                                menuViewModel.setShuffle("Shuffle Off")
                                onShuffleClick(false)
                            }
                        })

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_bluetooth,
                        text = "Bluetooth",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_add_to_playlist,
                        text = "Add to playlist",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_share,
                        text = "Share",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_ringtone,
                        text = "Use as a ringtone",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_sleep_timer,
                        text = "Sleep timer",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_drive_mode,
                        text = "Drive mode",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_go_to,
                        text = "Go to",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_volume,
                        text = "Volume",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})

                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_edit_audio,
                        text = "Edit audio",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        onClick = {},
                        textColor = textColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                if (!isSongMenu) {
                    MenuComponent(
                        width = width,
                        painter = R.drawable.ic_round_lyrics,
                        text = "Lyric",
                        backgroundColor = backgroundColor,
                        backgroundIconColor = backgroundIconColor,
                        iconColor = iconColor,
                        textColor = textColor,
                        onClick = {})
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}