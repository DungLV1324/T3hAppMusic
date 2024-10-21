package com.dunglv.appmusic.data.repository.heart

import com.dunglv.appmusic.data.local.room.dao.DaoHeart
import com.dunglv.appmusic.data.model.Heart
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeartRepoImpl @Inject constructor( private val daoHeart: DaoHeart) : HeartRepo  {
    override fun getAllMusicHeart() = flow {
        try {
            emit(daoHeart.getAllMusic())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun insertHeart(heart: Heart) = flow {
        try {
            heart.insertMusicToRoomDB(daoHeart).also { emit(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }

    override fun deleteAllHeart(uri: String) = flow {
        try {
            daoHeart.deleteHeart(uri)
            emit(uri)
        } catch (e: Exception) {
            e.printStackTrace()
            emit("")
        }
    }
}