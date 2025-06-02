package com.vishalag53.mp3.music.rhythmflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishalag53.mp3.music.rhythmflow.data.model.Audio
import com.vishalag53.mp3.music.rhythmflow.screen.mainactivity.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.screen.player.PlayerRootScreen
import kotlin.reflect.typeOf

@Composable
fun RootNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.Songs
    ) {

        composable<Screens.Main.Songs> {
            MainRootScreen(tab = "Songs", navController)
        }

        composable<Screens.Player>(
            typeMap = mapOf(
                typeOf<Audio>() to CustomNavType(Audio::class, Audio.serializer())
            )
        ) {
            val args = it.toRoute<Screens.Player>()

            PlayerRootScreen(
                args.audio,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}