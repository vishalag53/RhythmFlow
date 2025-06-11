package com.vishalag53.mp3.music.rhythmflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.main.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.player.player.PlayerRootScreen
import kotlin.reflect.typeOf

@Composable
fun RootNavigation(
    navController: NavHostController, audioList: List<Audio>
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(navController, audioList)
        playerGraph(navController)
    }
}

private fun NavGraphBuilder.mainGraph(
    navController: NavHostController, audioList: List<Audio>
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            tab = "Songs", navController = navController, audioList = audioList
        )
    }
}

private fun NavGraphBuilder.playerGraph(navController: NavHostController) {
    composable<Screens.Player>(
        typeMap = mapOf(
            typeOf<Audio>() to CustomNavType(Audio::class, Audio.serializer())
        )
    ) {
        val args = it.toRoute<Screens.Player>()

        PlayerRootScreen(
            args.audio, navigateBack = {
                navController.popBackStack()
            })
    }
}