package com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.MenuItemData
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.presentation.core.MenuComponent
import com.vishalag53.mp3.music.rhythmflow.presentation.core.MenuRow

@Composable
fun FolderMenu(
    folderMenuViewModel: FolderMenuViewModel,
    backgroundColor: Color,
    backgroundIconColor: Color,
    iconColor: Color,
    textColor: Color
) {
    val width = (LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2
    val folder = folderMenuViewModel.folder.collectAsStateWithLifecycle().value

    val menuItems = listOf(
        listOf(
            MenuItemData(R.drawable.ic_play, "Play") {},
            MenuItemData(R.drawable.ic_player_next, "Play next") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_add_to_playlist, "Add to playlist") {},
            MenuItemData(R.drawable.ic_add_to_playing_queue, "Add to playing queue") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_trash, "Trash") {},
            MenuItemData(R.drawable.ic_delete, "Delete") {}
        ),
        listOf(
            MenuItemData(R.drawable.ic_shuffle, "Shuffle all") {},
            MenuItemData(R.drawable.ic_share, "Share") {}
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
                    title = stringCapitalized(folder.name),
                    display = "${folder.length} songs | ${formatDuration(folder.totalTime)}",
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
                    modifier = Modifier.clickable {}
                )

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
                menuItems.forEach { row ->
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

                MenuComponent(
                    width = width,
                    painter = R.drawable.ic_hide,
                    text = "Hide folder",
                    backgroundColor = backgroundColor,
                    backgroundIconColor = backgroundIconColor,
                    iconColor = iconColor,
                    textColor = textColor,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}