package com.vishalag53.mp3.music.rhythmflow.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainTopAppBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        "Songs",
        "Folders",
        "Playlists",
        "Playback Speed",
        "Trash",
        "History",
        "Hidden Songs",
        "Settings"
    )

    val listState = rememberLazyListState()

    LaunchedEffect(selectedTabIndex) {
        listState.animateScrollToItem(selectedTabIndex)
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        itemsIndexed(tabs) { index, title ->
            FilterChip(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
                label = {
                    Text(
                        text = title,
                        style = if (selectedTabIndex == index) MaterialTheme.typography.bodyLarge
                        else MaterialTheme.typography.bodyMedium,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold
                        else FontWeight.Normal
                    )
                },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}