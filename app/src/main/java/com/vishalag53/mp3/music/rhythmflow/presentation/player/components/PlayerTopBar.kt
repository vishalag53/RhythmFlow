package com.vishalag53.mp3.music.rhythmflow.presentation.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerTopBar(navigateBack: () -> Unit, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Back",
            tint = Color(0xFF35363B),
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    navigateBack()
                })

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color(0xFF35363B),
            modifier = Modifier
                .size(36.dp)
                .clickable {
                    onMenuClick()
                }
        )
    }
}