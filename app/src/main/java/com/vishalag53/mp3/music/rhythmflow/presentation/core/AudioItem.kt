package com.vishalag53.mp3.music.rhythmflow.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.formatDuration
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized

@Composable
fun AudioItem(
    audio: Audio,
    onMenuClick: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelected: Boolean,
    setMenuAudio: () -> Unit,
    isSelectedItemEmpty: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 6.dp, bottom = 4.dp)
            .combinedClickable(
                onClick = if (isSelectedItemEmpty) onClick else onLongClick,
                onLongClick = onLongClick
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 6.dp)
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
                    text = formatDuration(audio.duration),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )

                if (isSelectedItemEmpty) {
                    IconButton(
                        onClick = {
                            setMenuAudio()
                            onMenuClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    if (isSelected) {
                        IconButton(
                            onClick = onLongClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        IconButton(
                            onClick = onLongClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}