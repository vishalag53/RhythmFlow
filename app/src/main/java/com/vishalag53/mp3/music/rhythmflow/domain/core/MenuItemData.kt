package com.vishalag53.mp3.music.rhythmflow.domain.core

data class MenuItemData(
    val icon: Int,
    val label: String,
    val onClick: () -> Unit
)