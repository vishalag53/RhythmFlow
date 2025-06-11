package com.vishalag53.mp3.music.rhythmflow.presentation.main.songs.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.navigation.Screens

@Composable
fun AudioItem(
    audio: Audio,
    navController: NavHostController,
//    onItemClick: () -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate(Screens.Player(audio))
//                onItemClick()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AudioTitleDisplayName(
                title = stringCapitalized(audio.title),
                display = stringCapitalized(audio.displayName),
                color = MaterialTheme.colorScheme.primary,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                modifierColumn = Modifier
                    .padding(6.dp)
                    .weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text(
                    text = formatDuration(audio.duration, context),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )

                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }

    }
}