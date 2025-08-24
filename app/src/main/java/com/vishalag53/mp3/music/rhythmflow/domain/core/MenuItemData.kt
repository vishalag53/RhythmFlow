package com.vishalag53.mp3.music.rhythmflow.domain.core

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    val imageVector: ImageVector,
    val label: String,
    val onClick: () -> Unit
)