package com.vishalag53.mp3.music.rhythmflow.presentation.folders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.Folder

@Composable
fun FolderItem(
    folder: Folder
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_music_folder),
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
                text = "${folder.length} songs | ${folder.totalTime}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6F),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_menu),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
