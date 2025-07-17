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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioProgressBar
import com.vishalag53.mp3.music.rhythmflow.presentation.core.AudioTitleDisplayName
import com.vishalag53.mp3.music.rhythmflow.domain.core.stringCapitalized
import com.vishalag53.mp3.music.rhythmflow.presentation.core.PlayerQueue
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerControllers
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerPlaybackSpeed
import com.vishalag53.mp3.music.rhythmflow.presentation.player.components.PlayerTopBar
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentBottomSheetContent
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel

@Composable
fun PlayerRootScreen(
    navigateBack: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel
) {
    val audio = basePlayerViewModel.currentSelectedAudio.collectAsStateWithLifecycle().value

    val playbackSpeed = remember { mutableFloatStateOf(1.0F) }

    LaunchedEffect(audio) {
        menuViewModel.setAudio(audio)
    }

    Scaffold(
        topBar = {
            PlayerTopBar(
                navigateBack = navigateBack,
                onMenuClick = {
                    parentViewModel.setMenuFrom(K.PLAYER)
                    parentUiState.value = ParentUiState(ParentBottomSheetContent.Menu)
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
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.PlaybackSpeed)
                        },
                        playbackSpeed = playbackSpeed.floatValue
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    PlayerQueue(
                        index = basePlayerViewModel.currentSelectedAudioIndex.collectAsStateWithLifecycle().value + 1,
                        length = basePlayerViewModel.audioList.collectAsStateWithLifecycle().value.size,
                        onClick = {
                            parentUiState.value = ParentUiState(ParentBottomSheetContent.PlayingQueue)
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
}