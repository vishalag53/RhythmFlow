package com.vishalag53.mp3.music.rhythmflow.presentation.others

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens

data class OthersItem(
    val label: String,
    @DrawableRes val iconRes: Int,
    val onClick: () -> Unit
)

@Composable
fun OthersRootScreen(navController: NavHostController) {
    val items = listOf(
        OthersItem("Themes", R.drawable.ic_themes) {},
        OthersItem("Widget", R.drawable.ic_widget) {},
        OthersItem("Equalizer", R.drawable.ic_equalizer1) {},
        OthersItem("Playback Speed", R.drawable.ic_playback_speed) { navController.navigate(Screens.PlaybackSpeed) },
        OthersItem("Volume Booster", R.drawable.ic_volume) {},
        OthersItem("Sleep Timer", R.drawable.ic_sleep_timer) {},
        OthersItem("Drive Mode", R.drawable.ic_drive_mode) {},
        OthersItem("Trash", R.drawable.ic_trash) {},
        OthersItem("History", R.drawable.ic_history) {},
        OthersItem("Hidden Songs", R.drawable.ic_hide) {},
        OthersItem("Setting", R.drawable.ic_settings) { navController.navigate(Screens.Settings)},
    )

    Column (
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 8.dp)
                    .clickable {
                        item.onClick()
                    }
            ) {
                IconButton(
                    onClick = item.onClick
                ) {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = item.onClick
                ) {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}