package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.vishalag53.mp3.music.rhythmflow.domain.core.MenuItemData

@Composable
fun MenuRow(
    width: Dp,
    backgroundColor: Color,
    backgroundIconColor: Color,
    iconColor: Color,
    textColor: Color,
    items: List<MenuItemData>
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEach { item ->
            MenuComponent(
                width = width,
                painter = item.icon,
                text = item.label,
                backgroundColor = backgroundColor,
                backgroundIconColor = backgroundIconColor,
                iconColor = iconColor,
                textColor = textColor,
                onClick = item.onClick
            )
        }
    }
}