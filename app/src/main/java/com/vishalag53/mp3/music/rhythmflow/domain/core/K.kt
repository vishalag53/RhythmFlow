package com.vishalag53.mp3.music.rhythmflow.domain.core

import com.vishalag53.mp3.music.rhythmflow.data.local.model.Audio

object K {
    const val SONGS = "Songs"
    const val PLAYLISTS = "Playlists"
    const val FOLDERS = "Folders"
    const val ARTISTS = "Artists"
    const val ALBUMS = "Albums"

    const val THEME_BLUE = "Blue"
    const val THEME_GREEN = "Green"

    const val NOTIFICATION_ID = 121
    const val NOTIFICATION_CHANNEL_NAME = "RhythmFlow Channel"
    const val NOTIFICATION_CHANNEL_ID = "RhythmFlow Channel Id"

    const val QUEUE_SONG_ALL = "Queue Song All"
    const val QUEUE_SONG_SEARCH = "Queue Song Search"
}

val dummyAudio = Audio(
    uriString = "",
    id = "",
    displayName = "",
    title = "",
    album = "",
    dateAdded = 0L,
    dateModified = 0L,
    artist = "",
    duration = 0L,
    path = "",
    folderName = "",
    size = 0L,
    bitrate = 0L
)
