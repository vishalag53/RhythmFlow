package com.vishalag53.mp3.music.rhythmflow.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vishalag53.mp3.music.rhythmflow.R

@Composable
internal fun SelectTabSearch(
    onClick: () -> Unit,
    selectTabName: String
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        TextButton(
            onClick = {
                onClick()
            }
        ) {
            Text(
                text = selectTabName,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                fontSize = 20.sp
            )
        }

        IconButton(
            onClick = {
                onClick()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Select tabs",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}