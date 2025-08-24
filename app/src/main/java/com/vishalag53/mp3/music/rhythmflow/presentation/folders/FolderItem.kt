package com.vishalag53.mp3.music.rhythmflow.presentation.folders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens
import com.vishalag53.mp3.music.rhythmflow.presentation.core.folderMenu.FolderMenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState

@Composable
fun FolderItem(
    folder: FolderData,
    navController: NavHostController,
    folderMenuViewModel: FolderMenuViewModel,
    parentUiState: MutableState<ParentUiState>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate(Screens.Folder(folder))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_music_folder),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(36.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = folder.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )

            Text(
                text = "${folder.length} songs | ${formatDuration(folder.totalTime)}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6F),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )
        }

        IconButton(
            onClick = {
                folderMenuViewModel.setFolder(folder)
                parentUiState.value = ParentUiState(ParentBottomSheetContent.FolderMenu)
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
