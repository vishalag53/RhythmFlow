package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SelectedMenu(
    isAllSelected: Boolean,
    onCancelClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    size: Int,
    onRenameClick: () -> Unit,
    onSongInfoClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFFFDCF9E))
        ) {
            if (isAllSelected) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Deselect all",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF324E82)
                        )
                    },
                    onClick = {
                        onCancelClick()
                        expanded = false
                    }
                )
            } else {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Select all",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF324E82)
                        )
                    },
                    onClick = {
                        onSelectAllClick()
                        expanded = false
                    }
                )
            }
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Move to",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF324E82)
                    )
                },
                onClick = {
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Copy to",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF324E82)
                    )
                },
                onClick = {
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Add to favorite",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF324E82)
                    )
                },
                onClick = {

                    expanded = false
                }
            )
            if (size == 1) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Rename",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF324E82)
                        )
                    },
                    onClick = {
                        onRenameClick()
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Song Info",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF324E82)
                        )
                    },
                    onClick = {
                        onSongInfoClick()
                        expanded = false
                    }
                )
            }
        }
    }
}