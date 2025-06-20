package com.vishalag53.mp3.music.rhythmflow.presentation.player.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioProgressBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerControllers
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.PlayerTopBar
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.components.SelectTabBox
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.player.playersheet.songinfo.SongInfoRootScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerRootScreen(
    audio: Audio,
    navigateBack: () -> Unit,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val width = LocalConfiguration.current.screenWidthDp.dp

    val playerUiState = remember { mutableStateOf(PlayerUiState()) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val showSheet = remember { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }

    LaunchedEffect(playerUiState.value) {
        showSheet.value =
            playerUiState.value.isPlayingQueue || playerUiState.value.isSongInfo || playerUiState.value.isPlayingBack || playerUiState.value.isMenu

        sheetContent.value = when {
            playerUiState.value.isPlayingQueue -> {
                {
                    SongQueueListsItem(
                        mainViewModel = mainViewModel, navController = navController
                    )
                }
            }

            playerUiState.value.isSongInfo -> {
                {
                    SongInfoRootScreen(audio = audio)
                }
            }

            else -> {
                {}
            }
        }
    }

    Scaffold(
        topBar = {
            PlayerTopBar(navigateBack)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFFFDCF9E),
                            0.41f to Color(0xFF99826D),
                            1.0f to Color(0xFF35363B)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AudioTitleDisplayName(
                            title = stringCapitalized(audio.title),
                            display = stringCapitalized(audio.displayName),
                            color = Color(0xFFFDCF9E),
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            modifierColumn = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        PlayerPlaybackSpeed()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    AudioProgressBar(Color(0xFF35363B), audio.duration)
                    Spacer(modifier = Modifier.height(10.dp))
                    PlayerControllers()
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                ) {
                    SelectTabBox(
                        text = "Playing Queue",
                        width = width,
                        topStart = 0.dp,
                        topEnd = 8.dp,
                        onClick = {
                            playerUiState.value = playerUiState.value.copy(isPlayingQueue = true)
                        })

                    Spacer(modifier = Modifier.width(0.185.dp))

                    SelectTabBox(
                        text = "Song Info",
                        width = width,
                        topEnd = 0.dp,
                        topStart = 8.dp,
                        onClick = {
                            playerUiState.value = playerUiState.value.copy(isSongInfo = true)
                        })
                }
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                playerUiState.value = PlayerUiState()
                showSheet.value = false
            },
            sheetState = sheetState,
            containerColor = Color(0xFF736659),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.DarkGray)
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF736659))
            ) {
                sheetContent.value()
            }
        }
    }
}