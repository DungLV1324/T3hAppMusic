package com.dunglv.appmusic.data.repository.foder

import android.app.Application
import android.provider.MediaStore
import com.dunglv.appmusic.data.model.Folder
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FolderRepoImpl @Inject constructor(private val application: Application) : FolderRepo {
    override fun getAllFolder() = flow {
        try {
            val projection = arrayOf(MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME)
            val listFolder: MutableList<Folder> = mutableListOf()
            application.contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                "${MediaStore.Audio.AudioColumns.DATA} LIKE ?",
                arrayOf("%mp3"),
                null
            )?.use { cursor ->
                val columnFolder = cursor.getColumnIndexOrThrow(
                    MediaStore.Audio.AudioColumns.BUCKET_DISPLAY_NAME
                )
                while (cursor.moveToNext()) {
                    Folder(
                        nameFolder = cursor.getString(columnFolder)
                    ).apply {
                        listFolder.add(this)
                    }
                }
            }
            emit(listFolder)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }
}