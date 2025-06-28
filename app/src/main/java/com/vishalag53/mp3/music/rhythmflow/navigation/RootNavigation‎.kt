package com.vishalag53.mp3.music.rhythmflow.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.main.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.PlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchRootScreen
import kotlin.reflect.typeOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    navController: NavHostController,
    audioList: List<Audio>,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(navController, audioList, mainViewModel, smallPlayerViewModel)
        playerGraph(navController, mainViewModel, smallPlayerViewModel)
        search(navController, mainViewModel, smallPlayerViewModel)
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    audioList: List<Audio>,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            tab = K.SONGS,
            navController = navController,
            audioList = audioList,
            mainViewModel = mainViewModel,
            smallPlayerViewModel = smallPlayerViewModel
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel
) {
    composable<Screens.Player>(
        typeMap = mapOf(
            typeOf<Audio>() to CustomNavType(Audio::class, Audio.serializer())
        )
    ) {
        val args = it.toRoute<Screens.Player>()

        PlayerRootScreen(
            audio = args.audio,
            navigateBack = {
                navController.popBackStack()
            },
            navController = navController,
            mainViewModel = mainViewModel,
            smallPlayerViewModel = smallPlayerViewModel
        )
    }
}

private fun NavGraphBuilder.search(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    smallPlayerViewModel: SmallPlayerViewModel
) {
    composable<Screens.Search> {
        SearchRootScreen(
            navController = navController,
            navigateBack = {
                navController.popBackStack()
            },
            mainViewModel = mainViewModel,
            smallPlayerViewModel = smallPlayerViewModel
        )
    }
}