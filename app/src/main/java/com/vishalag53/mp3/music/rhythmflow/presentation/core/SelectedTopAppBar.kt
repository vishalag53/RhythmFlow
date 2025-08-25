package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedTopAppBar(
    selectedSize: Int,
    onCancelClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isAllSelected: Boolean,
    onSelectAllClick: () -> Unit,
    onRenameClick: () -> Unit,
    onSongInfoClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "$selectedSize selected",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onCancelClick
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                onClick = onShareClick
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            SelectedMenu(
                isAllSelected = isAllSelected,
                onCancelClick = onCancelClick,
                onSelectAllClick = onSelectAllClick,
                size = selectedSize,
                onRenameClick = onRenameClick,
                onSongInfoClick = onSongInfoClick
            )
        },
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
}