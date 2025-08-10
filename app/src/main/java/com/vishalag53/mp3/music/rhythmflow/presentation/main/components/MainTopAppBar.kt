package com.vishalag53.mp3.music.rhythmflow.presentation.main.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Songs", "Folders", "Playlists", "Others")

    SecondaryScrollableTabRow (
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
                text = {
                    Text(
                        text = title,
                        style = if (selectedTabIndex == index) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary,
                        softWrap = true
                    )
                }
            )
        }
    }
}