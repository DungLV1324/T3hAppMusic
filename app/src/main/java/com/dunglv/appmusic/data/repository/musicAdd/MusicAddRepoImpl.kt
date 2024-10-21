package com.dunglv.appmusic.data.repository.musicAdd

import com.dunglv.appmusic.data.local.room.dao.DaoMusicAdd
import com.dunglv.appmusic.data.model.MusicAdd
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicAddRepoImpl @Inject constructor(private val daMusicAdd: DaoMusicAdd) : MusicAddRepo {
    override fun getAllMusicAdd() = flow {
        try {
            emit(daMusicAdd.getAllMusicAdd())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun insertMusicAdd(musicAdd: MusicAdd) = flow {
        try {
            musicAdd.insertMusicAdd(daMusicAdd).also {
                emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }
}