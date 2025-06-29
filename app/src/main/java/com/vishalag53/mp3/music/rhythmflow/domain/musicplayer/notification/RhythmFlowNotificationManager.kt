package com.vishalag53.mp3.music.rhythmflow.domain.musicplayer.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.vishalag53.mp3.music.rhythmflow.R
import com.vishalag53.mp3.music.rhythmflow.domain.core.K
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RhythmFlowNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exoPlayer: ExoPlayer
) {
    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession
    ) {
        buildNotification(mediaSession)
        startForeGroundNotificationService(mediaSessionService)
    }

    private fun startForeGroundNotificationService(mediaSessionService: MediaSessionService) {
        val notification = NotificationCompat.Builder(context, K.NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.ic_music_icon)
            .build()
        mediaSessionService.startForeground(K.NOTIFICATION_ID, notification)
    }

    @UnstableApi
    private fun buildNotification(mediaSession: MediaSession) {
        PlayerNotificationManager.Builder(
            context,
            K.NOTIFICATION_ID,
            K.NOTIFICATION_CHANNEL_ID
        )
            .setMediaDescriptionAdapter(
                RhythmFlowNotificationAdapter(
                    context = context,
                    pendingIntent = mediaSession.sessionActivity
                )
            )
            .setSmallIconResourceId(R.drawable.ic_music_icon)
            .setChannelImportance(NotificationManager.IMPORTANCE_LOW)
            .build()
            .also { playerNotificationManager ->
                playerNotificationManager.setPlayer(exoPlayer)
                playerNotificationManager.setMediaSessionToken(mediaSession.platformToken)
                playerNotificationManager.setUseNextActionInCompactView(true)
                playerNotificationManager.setUseRewindActionInCompactView(true)
                playerNotificationManager.setUseNextAction(true)
                playerNotificationManager.setUsePreviousAction(true)
                playerNotificationManager.setUsePlayPauseActions(true)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            K.NOTIFICATION_CHANNEL_ID,
            K.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}