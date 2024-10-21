package com.dunglv.appmusic.data.repository.musicPlayList

import com.dunglv.appmusic.data.local.room.dao.DaoMusicPL
import com.dunglv.appmusic.data.model.MusicPlayList
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicPLRepoImpl @Inject constructor(private val daoMusicPlayList: DaoMusicPL) : MusicPLRepo {
    override fun getAllMusicPL() = flow {
        try {
            emit(daoMusicPlayList.getAllMusicPlayList())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun insertMusicPL(musicPlayList: MusicPlayList) = flow {
        try {
            emit(musicPlayList.insertMusicPL(daoMusicPlayList))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }
}