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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DriveEta
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
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
        parentViewModel.requestRename(audio)
        onClose()
    }
    val onDeleteClick = {
        parentViewModel.requestDelete(audio)
    }

    val songMenuItems = listOf(
        listOf(
            MenuItemData(ImageVector.vectorResource(R.drawable.ic_rename), "Rename", onRenameClick),
            MenuItemData(
                ImageVector.vectorResource(R.drawable.ic_edit_audio_info),
                "Edit song info"
            ) {}
        ),
        listOf(
            MenuItemData(Icons.Default.DeleteForever, "Trash") {},
            MenuItemData(Icons.Default.Delete, "Delete", onDeleteClick)
        ),
        listOf(
            MenuItemData(ImageVector.vectorResource(R.drawable.ic_hide), "Hide") {},
            MenuItemData(Icons.Default.Lyrics, "Lyric") {}
        )
    )

    val generalMenuItems = listOf(
        listOf(
            MenuItemData(Icons.Default.LibraryAdd, "Add to playing queue") {},
            MenuItemData(
                ImageVector.vectorResource(R.drawable.ic_add_to_next_play),
                "Add to next play"
            ) {}
        ),
        listOf(
            MenuItemData(Icons.Default.GraphicEq, "Equalizer") {},
            MenuItemData(Icons.Default.Repeat, repeatText, onRepeatClick)
        ),
        listOf(
            MenuItemData(Icons.Default.Shuffle, shuffleText, onShuffleChange),
            MenuItemData(Icons.Default.Bluetooth, "Bluetooth") {}
        ),
        listOf(
            MenuItemData(Icons.AutoMirrored.Filled.PlaylistAdd, "Add to playlist") {},
            MenuItemData(Icons.Default.Share, "Share") {}
        ),
        listOf(
            MenuItemData(
                ImageVector.vectorResource(R.drawable.ic_ringtone),
                "Use as a ringtone"
            ) {},
            MenuItemData(Icons.Default.Timer, "Sleep timer") {}
        ),
        listOf(
            MenuItemData(Icons.Default.DriveEta, "Drive mode") {},
            MenuItemData(ImageVector.vectorResource(R.drawable.ic_go_to), "Go to") {}
        ),
        listOf(
            MenuItemData(Icons.AutoMirrored.Filled.VolumeUp, "Volume") {},
            MenuItemData(ImageVector.vectorResource(R.drawable.ic_edit_audio), "Edit audio") {}
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
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF35363B)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
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
                        imageVector = Icons.Default.Lyrics,
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