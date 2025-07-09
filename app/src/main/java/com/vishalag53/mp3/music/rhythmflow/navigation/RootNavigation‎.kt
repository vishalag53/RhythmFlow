package com.vishalag53.mp3.music.rhythmflow.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchRootScreen
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(
            navController = navController,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel
        )

        playerGraph(
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel
        )

        search(
            navController = navController,
            mainViewModel = mainViewModel,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    mainViewModel: MainViewModel,
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            tab = K.SONGS,
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            startNotificationService = startNotificationService,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
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
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel
        )
    }
}

private fun NavGraphBuilder.search(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
) {
    composable<Screens.Search> {
        SearchRootScreen(
            navController = navController,
            navigateBack = {
                navController.popBackStack()
            },
            mainViewModel = mainViewModel,
            basePlayerViewModel = basePlayerViewModel,
            startNotificationService = startNotificationService,
            menuViewModel = menuViewModel
        )
    }
}