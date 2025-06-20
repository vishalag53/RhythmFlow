package com.vishalag53.mp3.music.rhythmflow.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.main.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.other.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.PlayerRootScreen
import kotlin.reflect.typeOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    navController: NavHostController, audioList: List<Audio>, mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(navController, audioList, mainViewModel)
        playerGraph(navController, mainViewModel)
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavHostController, audioList: List<Audio>, mainViewModel: MainViewModel
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            tab = K.SONGS,
            navController = navController,
            audioList = audioList,
            mainViewModel = mainViewModel
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
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
            mainViewModel = mainViewModel
        )
    }
}