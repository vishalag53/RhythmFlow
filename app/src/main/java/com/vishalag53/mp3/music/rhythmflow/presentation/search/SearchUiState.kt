package com.vishalag53.mp3.music.rhythmflow.presentation.search

data class SearchUiState(
    val isHistory: Boolean = true,
    val isExpandedSongs: Boolean = false,
    val isExpandedPlaylists: Boolean = false,
    val isExpandedFolders: Boolean = false,
    val isExpandedAlbums: Boolean = false,
    val isExpandedArtists: Boolean = false
)