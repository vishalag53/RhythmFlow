package com.vishalag53.mp3.music.rhythmflow.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchUiState

@Composable
fun SearchResult(
    resultText: String, searchUiState: SearchUiState, onExpandToggle: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$resultText (0)",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = {
                when (resultText) {
                    K.SONGS -> onExpandToggle(!searchUiState.isExpandedSongs)
                    K.PLAYLISTS -> onExpandToggle(!searchUiState.isExpandedPlaylists)
                    K.FOLDERS -> onExpandToggle(!searchUiState.isExpandedFolders)
                    K.ALBUMS -> onExpandToggle(!searchUiState.isExpandedAlbums)
                    K.ARTISTS -> onExpandToggle(!searchUiState.isExpandedArtists)
                }
            }) {
                var icon = Icons.Default.KeyboardArrowDown
                when (resultText) {
                    K.SONGS -> {
                        icon = if (searchUiState.isExpandedSongs) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown
                    }

                    K.PLAYLISTS -> {
                        icon = if (searchUiState.isExpandedPlaylists) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown
                    }

                    K.FOLDERS -> {
                        icon = if (searchUiState.isExpandedFolders) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown
                    }

                    K.ALBUMS -> {
                        icon = if (searchUiState.isExpandedAlbums) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown
                    }

                    K.ARTISTS -> {
                        icon = if (searchUiState.isExpandedArtists) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown
                    }
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}