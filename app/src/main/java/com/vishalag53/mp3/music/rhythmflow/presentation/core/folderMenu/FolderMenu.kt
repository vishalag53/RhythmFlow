package com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
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
    textColor: Color,
    onInfoClick: () -> Unit,
) {
    val width = (LocalConfiguration.current.screenWidthDp.dp - 24.dp) / 2
    val folder = folderMenuViewModel.folder.collectAsStateWithLifecycle().value

    val menuItems = listOf(
        listOf(
            MenuItemData(Icons.Default.PlayArrow, "Play") {},
            MenuItemData(Icons.Default.SkipNext, "Play next") {}
        ),
        listOf(
            MenuItemData(Icons.AutoMirrored.Filled.PlaylistAdd, "Add to playlist") {},
            MenuItemData(Icons.Default.LibraryAdd, "Add to playing queue") {}
        ),
        listOf(
            MenuItemData(Icons.Default.DeleteForever, "Trash") {},
            MenuItemData(Icons.Default.Delete, "Delete") {}
        ),
        listOf(
            MenuItemData(Icons.Default.Shuffle, "Shuffle all") {},
            MenuItemData(Icons.Default.Share, "Share") {}
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
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hide),
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