package com.vishalag53.mp3.music.rhythmflow.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishalag53.mp3.music.rhythmflow.domain.core.FolderData
import com.vishalag53.mp3.music.rhythmflow.presentation.core.baseplayer.BasePlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.core.menu.MenuViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.folder.FolderRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.main.MainRootScreen
import com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity.MainViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.parent.ParentUiState
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
    searchViewModel: SearchViewModel
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
            parentUiState = parentUiState
        )

        playerGraph(
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState
        )

        search(
            navController = navController,
            mainViewModel = mainViewModel,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState,
            searchViewModel = searchViewModel
        )

        folder(
            navController = navController,
            mainViewModel = mainViewModel,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState
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
    parentUiState: MutableState<ParentUiState>
) {
    composable<Screens.Main.Songs> {
        MainRootScreen(
            navController = navController,
            basePlayerViewModel = basePlayerViewModel,
            startNotificationService = startNotificationService,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel,
            parentUiState = parentUiState
        )
    }
}

private fun NavGraphBuilder.playerGraph(
    navController: NavHostController,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>
) {
    composable<Screens.Player> {
        PlayerRootScreen(
            navigateBack = {
                navController.popBackStack()
            },
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            parentUiState = parentUiState
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
    searchViewModel: SearchViewModel
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
            searchViewModel = searchViewModel
        )
    }
}

private fun NavGraphBuilder.folder(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startNotificationService: () -> Unit,
    basePlayerViewModel: BasePlayerViewModel,
    menuViewModel: MenuViewModel,
    parentUiState: MutableState<ParentUiState>
) {
    composable<Screens.Folder>(
        typeMap = mapOf(
            typeOf<FolderData>() to CustomNavType(FolderData::class, FolderData.serializer())
        )
    ) {
        val args = it.toRoute<Screens.Folder>()

        FolderRootScreen(
            folder = args.folder,
            navController = navController,
            startNotificationService = startNotificationService,
            basePlayerViewModel = basePlayerViewModel,
            menuViewModel = menuViewModel,
            mainViewModel = mainViewModel,
            parentUiState = parentUiState
        )
    }
}