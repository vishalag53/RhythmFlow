package com.vishalag53.mp3.music.rhythmflow.presentation.core.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Info(
    about: List<String>,
    aboutInfo: List<String>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            for (i in about.indices) {
                if (aboutInfo[i] != "<unknown>") {
                    InfoCard(about[i], aboutInfo[i])
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}