package com.dunglv.appmusic.data.repository.album

import com.dunglv.appmusic.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepo {
    fun getAllAlbum(): Flow<List<Album>>
}