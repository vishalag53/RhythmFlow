package com.vishalag53.mp3.music.rhythmflow.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.search.SearchViewModel
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun RootNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    searchViewModel: SearchViewModel,
    parentViewModel: ParentViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.Main.Songs
    ) {
        mainGraph(
            navController = navController,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel,
            parentUiState = parentUiState,
            parentViewModel = parentViewModel
        )

        playerGraph(
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState,
            parentViewModel = parentViewModel
        )

        search(
            navController = navController,
            mainViewModel = mainViewModel,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState,
            searchViewModel = searchViewModel,
            parentViewModel = parentViewModel
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
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel,
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            startNotificationService = startNotificationService,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel,
            parentUiState = parentUiState,
            parentViewModel = parentViewModel
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    parentViewModel: ParentViewModel,
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
            menuViewModel = menuViewModel,
            parentUiState = parentUiState,
            parentViewModel = parentViewModel
        )
    }
}

private fun NavGraphBuilder.search(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>,
    searchViewModel: SearchViewModel,
    parentViewModel: ParentViewModel,
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
            menuViewModel = menuViewModel,
            parentUiState = parentUiState,
            searchViewModel = searchViewModel,
            parentViewModel = parentViewModel
        )
    }
}