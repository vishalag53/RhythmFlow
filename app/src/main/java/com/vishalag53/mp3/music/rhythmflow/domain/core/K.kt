package com.vishalag53.mp3.music.rhythmflow.domain.core

import com.vishalag53.mp3.music.rhythmflow.data.model.Audio

object K {
    const val SONGS = "Songs"
    const val PLAYLISTS = "Playlists"
    const val FOLDERS = "Folders"
    const val ARTISTS = "Artists"
    const val ALBUMS = "Albums"
    const val GENRES = "Genres"
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
