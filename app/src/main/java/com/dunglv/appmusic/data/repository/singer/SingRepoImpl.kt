package com.dunglv.appmusic.data.repository.singer

import android.app.Application
import android.provider.MediaStore
import com.dunglv.appmusic.data.model.Singer
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SingRepoImpl @Inject constructor(private val application: Application) : SingRepo {
    override fun getAllSinger() = flow {
        try {
            val listSinger: MutableList<Singer> = mutableListOf()
            val projection = arrayOf(MediaStore.Audio.ArtistColumns.ARTIST)
            application.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns.DATA} LIKE ?",
                arrayOf("%mp3"),
                null
            )?.use { cursor ->

                val columnArtist =
                    (cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST))

                while (cursor.moveToNext()) {
                    Singer(
                        nameSinger = cursor.getString(columnArtist)
                    ).apply {
                        listSinger.add(this)
                    }
                }
            }
            emit(listSinger)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }
}
