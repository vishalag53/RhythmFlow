package com.vishalag53.mp3.music.rhythmflow.presentation.mainactivity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.service.RhythmFlowService
import com.vishalag53.mp3.music.rhythmflow.navigation.RootNavigation
import com.vishalag53.mp3.music.rhythmflow.presentation.core.Loading
import com.vishalag53.mp3.music.rhythmflow.presentation.smallplayer.SmallPlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.player.PlayerViewModel
import com.vishalag53.mp3.music.rhythmflow.presentation.storagepermission.AskStoragePermission
import com.vishalag53.mp3.music.rhythmflow.presentation.theme.RhythmFlowTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var isServiceRunning = false

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
        enableEdgeToEdge()
        setContent {
            RhythmFlowTheme {
                val navController = rememberNavController()
                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.READ_EXTERNAL_STORAGE
                )
                val lifecycleOwner = LocalLifecycleOwner.current
                val smallPlayerViewModel by viewModels<SmallPlayerViewModel>()
                val playerViewModel by viewModels<PlayerViewModel>()

                DisposableEffect(key1 = lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            permissionState.launchPermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }
                if (permissionState.status.isGranted) {
                    val isLoading = mainViewModel.isLoading.collectAsStateWithLifecycle()
                    var audioList = mainViewModel.audioList.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        if (audioList.value.isEmpty()) {
                            mainViewModel.loadAudioData()
                        }
                    }

                    if (isLoading.value) {
                        Loading()
                    } else {
                        RootNavigation(
                            navController = navController,
                            audioList = audioList.value,
                            mainViewModel = mainViewModel,
                            smallPlayerViewModel = smallPlayerViewModel,
                            playerViewModel = playerViewModel,
                            startNotificationService = { startService() }
                        )
                    }
                } else {
                    AskStoragePermission()
                }
            }
        }
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, RhythmFlowService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            isServiceRunning = true
        }
    }

    private fun showSplashScreen() {
        var keepSplashOpened = true

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.keepSplashOn.collectLatest {
                    keepSplashOpened = it
                }
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
    }
}