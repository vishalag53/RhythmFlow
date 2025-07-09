package com.vishalag53.mp3.music.rhythmflow.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel

@Composable
fun SelectTabMainModalBottomSheet(mainViewModel: MainViewModel, onClose: () -> Unit) {
    val selectTabNames = listOf(
        K.SONGS,
        K.PLAYLISTS,
        K.FOLDERS,
        K.ARTISTS,
        K.ALBUMS
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "View my",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(6.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 0.25.dp,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth()
            )
        }

        selectTabNames.forEach { tab ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .clickable {
                        mainViewModel.setSelectTabName(tab)
                        onClose()
                    }
            ) {
                if (mainViewModel.selectTabName.collectAsStateWithLifecycle().value == tab) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Spacer(modifier = Modifier.width(24.dp))
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = tab,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}