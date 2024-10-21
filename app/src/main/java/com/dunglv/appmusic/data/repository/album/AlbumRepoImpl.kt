package com.dunglv.appmusic.data.repository.album

import android.app.Application
import android.provider.MediaStore
import com.dunglv.appmusic.data.model.Album
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AlbumRepoImpl @Inject constructor(private val application: Application) : AlbumRepo {
    override fun getAllAlbum() = flow {
        try {
            val projection = arrayOf(
                MediaStore.Audio.Artists.Albums._ID,
                MediaStore.Audio.Artists.Albums.ALBUM,
                MediaStore.Audio.Artists.Albums.ARTIST,
                MediaStore.Audio.Artists.Albums.ALBUM_ART,
                MediaStore.Audio.Artists.Albums.NUMBER_OF_SONGS
            )
            val listAlbum: MutableList<Album> = mutableListOf()
            val sortOrder = MediaStore.Audio.Media.ALBUM + " ASC"
            application.contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val columnArtist =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums.ARTIST)
                val columnAlbum =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums.ALBUM)
                while (cursor.moveToNext()) {
                    val sing = cursor.getString(columnArtist)
                    val album = cursor.getString(columnAlbum)
                    Album(
                        nameSing = sing,
                        name = album
                    ).apply {
                        listAlbum.add(this)
                    }
                }
            }
            emit(listAlbum)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }
}