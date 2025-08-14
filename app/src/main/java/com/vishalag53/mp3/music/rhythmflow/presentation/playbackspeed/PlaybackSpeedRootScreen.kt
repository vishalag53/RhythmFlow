package com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.deleteAllPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.deletePlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.data.roomdatabase.playbackspeed.upsertPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed.components.PlaybackSpeedItem
import com.vishalag53.mp3.music.rhythmflow.presentation.playbackspeed.components.PlaybackSpeedTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaybackSpeedRootScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val playbackSpeedViewModel = hiltViewModel<PlaybackSpeedViewModel>()
    val allPlaybackSpeed =
        playbackSpeedViewModel.allPlaybackSpeed.collectAsStateWithLifecycle().value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PlaybackSpeedTopBar(
                onBackClick = { navController.popBackStack() },
                onResetClick = {
                    allPlaybackSpeed.forEach { item ->
                        upsertPlaybackSpeed(
                            name = item.name,
                            playbackSpeed = 1.0f,
                            context = context,
                        )
                    }
                },
                onDeleteClick = {
                    coroutineScope.launch {
                        deleteAllPlaybackSpeed(context = context)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                allPlaybackSpeed.forEach { item ->
                    PlaybackSpeedItem(
                        item = item,
                        onResetClick = {
                            upsertPlaybackSpeed(
                                name = item.name,
                                playbackSpeed = 1.0f,
                                context = context,
                            )
                        },
                        onDeleteClick = {
                            coroutineScope.launch {
                                deletePlaybackSpeed(
                                    context = context,
                                    name = item.name
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}