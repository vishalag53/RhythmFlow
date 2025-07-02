package com.vishalag53.mp3.music.rhythmflow.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerEvents
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchRootScreen
import kotlin.reflect.typeOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    navController: NavHostController,
    audioList: List<Audio>,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel,
    startNotificationService: () -> Unit,
    playerViewModel: PlayerViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(
            navController = navController,
            audioList = audioList,
            smallPlayerViewModel = smallPlayerViewModel,
            startNotificationService = startNotificationService,
            playerViewModel = playerViewModel
        )

        playerGraph(
            navController = navController,
            playerViewModel = playerViewModel
        )

        search(
            navController = navController,
            mainViewModel = mainViewModel,
            smallPlayerViewModel = smallPlayerViewModel,
            startNotificationService = startNotificationService,
            playerViewModel = playerViewModel
        )
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    audioList: List<Audio>,
    smallPlayerViewModel: SmallPlayerViewModel,
    startNotificationService: () -> Unit,
    playerViewModel: PlayerViewModel
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            tab = K.SONGS,
            navController = navController,
            audioList = audioList,
            smallPlayerViewModel = smallPlayerViewModel,
            startNotificationService = startNotificationService,
            playerViewModel = playerViewModel
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    composable<Screens.Player>(
        typeMap = mapOf(
            typeOf<Audio>() to CustomNavType(Audio::class, Audio.serializer())
        )
    ) {
        PlayerRootScreen(
            navigateBack = {
                navController.popBackStack()
            },
            audio = playerViewModel.currentSelectedAudio.collectAsStateWithLifecycle().value,
            audioList = playerViewModel.audioList.collectAsStateWithLifecycle().value,
            progress = playerViewModel.progress.collectAsStateWithLifecycle().value,
            progressString = playerViewModel.progressString.collectAsStateWithLifecycle().value,
            onProgressChange = { progress ->
                playerViewModel.onPlayerEvents(PlayerEvents.UpdateProgress(progress))
            },
            onPrev = {
                playerViewModel.onPlayerEvents(PlayerEvents.SeekToPreviousItem)
            },
            onNext = {
                playerViewModel.onPlayerEvents(PlayerEvents.SeekToNextItem)
            },
            onBackward = {
                playerViewModel.onPlayerEvents(PlayerEvents.Backward)
            },
            onForward = {
                playerViewModel.onPlayerEvents(PlayerEvents.Forward)
            },
            onStart = {
                playerViewModel.onPlayerEvents(PlayerEvents.PlayPause)
            },
            isAudioPlaying = playerViewModel.isPlaying.collectAsStateWithLifecycle().value,

        )
    }
}

private fun NavGraphBuilder.search(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel,
    startNotificationService: () -> Unit,
    playerViewModel: PlayerViewModel
) {
    composable<Screens.Search> {
        SearchRootScreen(
            navController = navController,
            navigateBack = {
                navController.popBackStack()
            },
            mainViewModel = mainViewModel,
            smallPlayerViewModel = smallPlayerViewModel,
            startNotificationService = startNotificationService,
            playerViewModel = playerViewModel
        )
    }
}