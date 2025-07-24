package com.vishalag53.mp3.music.rhythmflow.presentation.core.drawer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R

data class DrawerItem(
    val label: String,
    @DrawableRes val iconRes: Int
)

@Composable
fun Drawer(width: Dp) {
    val isDarkTheme = isSystemInDarkTheme()
    val logo = if (isDarkTheme) R.drawable.ic_logo_dark_splash
    else R.drawable.ic_logo_light_splash

    val items = listOf(
        DrawerItem("Themes", R.drawable.ic_themes),
        DrawerItem("Widget", R.drawable.ic_widget),
        DrawerItem("Equalizer", R.drawable.ic_equalizer1),
        DrawerItem("Playback Speed", R.drawable.ic_playback_speed),
        DrawerItem("Volume Booster", R.drawable.ic_volume),
        DrawerItem("Sleep Timer", R.drawable.ic_sleep_timer),
        DrawerItem("Drive Mode", R.drawable.ic_drive_mode),
        DrawerItem("Trash", R.drawable.ic_trash),
        DrawerItem("History", R.drawable.ic_history),
        DrawerItem("Hidden Songs", R.drawable.ic_hide),
        DrawerItem("Settings", R.drawable.ic_settings),
    )

    ModalDrawerSheet(
        modifier = Modifier.width(width),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerTonalElevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 2.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(86.dp)
                            .width(88.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.background,
                                        Color(0xFF3078AB)
                                    )
                                ),
                                shape = RoundedCornerShape(100.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(logo),
                            contentDescription = null,
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Text(
                        text = "Rhythm flow",
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            items.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            painter = painterResource(item.iconRes),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {},
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(36.dp)
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Exit",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                selected = false,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_exit),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {},
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(36.dp)
                )
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}