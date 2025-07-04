package com.vishalag53.mp3.music.rhythmflow.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioProgressBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.PlayerQueue
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.Menu
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playbackspeed.PlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerControllers
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerTopBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.playingqueue.SongQueueListsItem
import com.vishalag53.mp3.music.rhythmflow.presentation.core.repeat.Repeat
import com.vishalag53.mp3.music.rhythmflow.presentation.core.songinfo.SongInfoRootScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerRootScreen(
    navigateBack: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val audio = basePlayerViewModel.currentSelectedAudio.collectAsStateWithLifecycle().value

    val playerUiStateSaver: Saver<PlayerUiState, *> = Saver(save = {
        listOf(it.sheet::class.simpleName ?: "")
    }, restore = {
        val sheet = when (it.firstOrNull()) {
            PlayerBottomSheetContent.PlayerQueue::class.simpleName -> PlayerBottomSheetContent.PlayerQueue
            PlayerBottomSheetContent.SongInfo::class.simpleName -> PlayerBottomSheetContent.SongInfo
            else -> PlayerBottomSheetContent.None
        }
        PlayerUiState(sheet = sheet)
    })

    val playerUiState = rememberSaveable(stateSaver = playerUiStateSaver) {
        mutableStateOf(PlayerUiState())
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val showSheet = rememberSaveable { mutableStateOf(false) }
    val sheetContent = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val scope = rememberCoroutineScope()

    val modalBottomSheetBackgroundColor = remember { mutableStateOf(Color(0xFF736659)) }

    val playbackSpeed = remember { mutableFloatStateOf(1.0F) }
    val repeatMode = remember { mutableStateOf( "Repeat Off") }

    LaunchedEffect(playerUiState.value.sheet) {
        when (playerUiState.value.sheet) {
            PlayerBottomSheetContent.PlayerQueue -> {
                modalBottomSheetBackgroundColor.value = Color(0xFF736659)
                showSheet.value = true
                sheetContent.value = {
                    SongQueueListsItem(audioList = basePlayerViewModel.audioList.collectAsStateWithLifecycle().value)
                }
                scope.launch { sheetState.show() }
            }

            PlayerBottomSheetContent.SongInfo -> {
                modalBottomSheetBackgroundColor.value = Color(0xFF736659)
                showSheet.value = true
                sheetContent.value = {
                    SongInfoRootScreen(audio = audio)
                }
                scope.launch { sheetState.show() }
            }

            PlayerBottomSheetContent.PlaybackSpeed -> {
                showSheet.value = true
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                sheetContent.value = {
                    PlaybackSpeed(
                        onClose = {
                            playerUiState.value = PlayerUiState(PlayerBottomSheetContent.None)
                        },
                        playbackSpeed = playbackSpeed.floatValue,
                        onPlaybackSpeedChange = { speed ->
                            playbackSpeed.floatValue = speed
                        },
                        onPlaybackSpeedChangeBasePlayer = { speed ->
                            basePlayerViewModel.onBasePlayerEvents(
                                BasePlayerEvents.PlayBackSpeed(
                                    speed
                                )
                            )
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            PlayerBottomSheetContent.PlayingMenu -> {
                showSheet.value = true
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                sheetContent.value = {
                    Menu(
                        audio = audio,
                        onInfoClick = {
                            playerUiState.value = PlayerUiState(PlayerBottomSheetContent.SongInfo)
                        },
                        onRepeatClick = {
                            playerUiState.value = PlayerUiState(PlayerBottomSheetContent.Repeat)
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            PlayerBottomSheetContent.Repeat -> {
                showSheet.value = true
                modalBottomSheetBackgroundColor.value = Color(0xFFFDCF9E)
                sheetContent.value = {
                    Repeat(
                        repeatMode = repeatMode.value,
                        onClose = {
                            playerUiState.value = PlayerUiState(PlayerBottomSheetContent.None)
                        },
                        onRepeatChange = {  repeat ->
                            repeatMode.value = repeat
                        },
                        onRepeatChangeBasePlayer = {
                            basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SetRepeatMode(it))
                        }
                    )
                }
                scope.launch { sheetState.show() }
            }

            PlayerBottomSheetContent.None -> {
                showSheet.value = false
                scope.launch { sheetState.hide() }
            }
        }
    }

    Scaffold(
        topBar = {
            PlayerTopBar(
                navigateBack = navigateBack,
                onMenuClick = {
                    playerUiState.value = PlayerUiState(PlayerBottomSheetContent.PlayingMenu)
                }
            )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Center
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
                    Spacer(modifier = Modifier.width(4.dp))
                    PlayerPlaybackSpeed(
                        onOpen = {
                            playerUiState.value =
                                PlayerUiState(PlayerBottomSheetContent.PlaybackSpeed)
                        },
                        playbackSpeed = playbackSpeed.floatValue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    PlayerQueue(
                        index = basePlayerViewModel.currentSelectedAudioIndex.collectAsStateWithLifecycle().value + 1,
                        length = basePlayerViewModel.audioList.collectAsStateWithLifecycle().value.size,
                        onClick = {
                            playerUiState.value =
                                PlayerUiState(PlayerBottomSheetContent.PlayerQueue)
                        },
                        color = Color(0xFFFDCF9E)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                AudioProgressBar(
                    inactiveColor = Color(0xFF35363B),
                    audioDuration = audio.duration,
                    progress = basePlayerViewModel.progress.collectAsStateWithLifecycle().value,
                    progressString = basePlayerViewModel.progressString.collectAsStateWithLifecycle().value,
                    onProgressChange = { progress ->
                        basePlayerViewModel.onBasePlayerEvents(
                            BasePlayerEvents.UpdateProgress(
                                progress
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                PlayerControllers(
                    onPrev = {
                        basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToPreviousItem)
                    },
                    onNext = {
                        basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.SeekToNextItem)
                    },
                    onBackward = {
                        basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.Backward)
                    },
                    onForward = {
                        basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.Forward)
                    },
                    onStart = {
                        basePlayerViewModel.onBasePlayerEvents(BasePlayerEvents.PlayPause)
                    },
                    isAudioPlaying = basePlayerViewModel.isPlaying.collectAsStateWithLifecycle().value
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    if (showSheet.value) {
        ModalBottomSheet(
            modifier = Modifier.statusBarsPadding(),
            onDismissRequest = {
                playerUiState.value = PlayerUiState()
                scope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            containerColor = modalBottomSheetBackgroundColor.value,
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
            }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = screenHeight * 0.7F)
                    .background(Color(0xFF736659))
            ) {
                sheetContent.value()
            }
        }
    }
}