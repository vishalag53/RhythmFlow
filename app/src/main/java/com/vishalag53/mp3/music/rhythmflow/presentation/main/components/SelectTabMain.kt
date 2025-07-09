package com.vishalag53.mp3.music.rhythmflow.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel

@Composable
internal fun SelectTabMain(mainViewModel: MainViewModel, onSelectTabMainClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .clickable {
                onSelectTabMainClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = mainViewModel.selectTabName.collectAsStateWithLifecycle().value,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                onSelectTabMainClick()
            }
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            painter = painterResource(R.drawable.ic_back),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "Select tabs",
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    onSelectTabMainClick()
                }
        )
    }
}