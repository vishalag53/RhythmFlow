package com.vishalag53.mp3.music.rhythmflow.presentation.core.playbackspeed

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R

@SuppressLint("DefaultLocale")
@Composable
fun PlaybackSpeed(
    onClose: () -> Unit,
    playbackSpeed: Float,
    onPlaybackSpeedChange: (Float) -> Unit,
    onPlaybackSpeedChangeBasePlayer: (Float) -> Unit
) {
    val playbackSpeedList1 = listOf(
        0.75F, 1.0F, 1.25F, 1.5F
    )
    val playbackSpeedList2 = listOf(
        1.75F, 2.0F, 2.25F, 2.50F
    )

    val context = LocalContext.current
    val playbackSpeedText = remember { mutableStateOf(playbackSpeed.toString()) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_playback_speed),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text(
                    text = "Set Playback Speed: ${playbackSpeed}x",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        val speed = playbackSpeedText.value.toFloatOrNull()
                        if (speed != null) {
                            val newSpeed = when (speed) {
                                in 0.3F..5.0F -> speed.minus(0.05F)
                                in 0.25F..0.3F -> 0.25F
                                else -> speed
                            }
                            playbackSpeedText.value = String.format("%.2f", newSpeed)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                TextField(
                    value = playbackSpeedText.value,
                    onValueChange = {
                        playbackSpeedText.value = it
                    },
                    modifier = Modifier
                        .width(132.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White
                    ),
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                val speed = playbackSpeedText.value.toFloatOrNull()
                                if (speed != null) {
                                    playbackSpeedText.value = String.format("%.2f", speed)
                                    val newSpeed = playbackSpeedText.value.toFloat()

                                    if (newSpeed in 0.25f..5.0f) {
                                        onPlaybackSpeedChange(newSpeed)
                                        onPlaybackSpeedChangeBasePlayer(newSpeed)
                                        onClose()
                                    } else {
                                        if (newSpeed < 0.25F) {
                                            playbackSpeedText.value = "0.25"
                                        } else {
                                            playbackSpeedText.value = "5.0"
                                        }
                                        Toast
                                            .makeText(context, "Please enter a speed between 0.25x and 5.0x.", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    Toast
                                        .makeText(context, " Invalid speed, Please enter a number.", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        ) {
                            Text(
                                text = "SET",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                )

                IconButton(
                    onClick = {
                        val speed = playbackSpeedText.value.toFloatOrNull()
                        if (speed != null) {
                            val newSpeed = when (speed) {
                                in 0.25F..4.95F -> speed.plus(0.05F)
                                in 4.95F..5.0F -> 5.0F
                                else -> speed
                            }
                            playbackSpeedText.value = String.format("%.2f", newSpeed)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                for (i in playbackSpeedList1) {
                    PlaybackSpeedSelectButton(
                        text = i,
                        onClose = onClose,
                        onPlaybackSpeedChange = onPlaybackSpeedChange,
                        onPlaybackSpeedChangeBasePlayer = onPlaybackSpeedChangeBasePlayer
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                for (i in playbackSpeedList2) {
                    PlaybackSpeedSelectButton(
                        text = i,
                        onClose = onClose,
                        onPlaybackSpeedChange = onPlaybackSpeedChange,
                        onPlaybackSpeedChangeBasePlayer = onPlaybackSpeedChangeBasePlayer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}