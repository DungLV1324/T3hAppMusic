package com.dunglv.appmusic.data.repository.music

import android.app.Application
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.dunglv.appmusic.data.local.room.dao.DaoMusic
import com.dunglv.appmusic.data.model.Music
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepoImpl @Inject constructor(
    private val daoMusic: DaoMusic,
    private val application: Application
) : MusicRepo {

    private val listAudio: MutableList<Music> = mutableListOf()
    override fun insertMusic(music: Music) = flow {
        try {
            music.insertMusic(daoMusic).also {
                emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }

    override fun getAllMusicNoImage() = flow {
        try {
            val projection = arrayOf(
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.Artists.Albums.ALBUM,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME
            )
            application.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns.DATA} LIKE ?",
                arrayOf("%mp3"),
                null
            )?.use { cursor ->
                val columnData =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
                val columnTitle =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
                val columnArtist =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST)
                val columnAlbum =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.Albums.ALBUM)
                val columnFolder =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME)
                val columnDuration =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
                while (cursor.moveToNext()) {
                    cursor.getLong(columnDuration).takeIf { it > 0 }?.let {
                        val pathImage = cursor.getString(columnData)
                        val song = cursor.getString(columnTitle)
                        val sing = cursor.getString(columnArtist)
                        val listAlbum: String = cursor.getString(columnAlbum)
                        val folder: String = cursor.getString(columnFolder)

                        Music(
                            uri = pathImage,
                            nameSing = sing,
                            nameSong = song,
                            album = listAlbum,
                            duration = it,
                            nameFolder = folder,
                            isSelect = false
                        ).apply {
                            isSelect = false
                            listAudio.add(this)
                        }
                    }
                }
            }

            emit(listAudio)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun getAllMusicImage() = flow {
        try {
            val retriever = MediaMetadataRetriever()
            listAudio.forEach {
                try {
                    retriever.setDataSource(it.uri)
                    retriever.embeddedPicture?.let { bytes ->
                        it.imageBitmap =
                            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
            retriever.release()
            emit(listAudio)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(listAudio)
        }
    }
}
