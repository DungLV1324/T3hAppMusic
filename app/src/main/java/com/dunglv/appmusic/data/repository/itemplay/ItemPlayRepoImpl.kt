package com.dunglv.appmusic.data.repository.itemplay

import com.dunglv.appmusic.data.local.room.dao.DaoItemPlayList
import com.dunglv.appmusic.data.model.ItemPlayList
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemPlayRepoImpl  @Inject constructor(private val daoPlayList: DaoItemPlayList): ItemPlayRepo {
    override fun insertItemPlay(itemPlayList: ItemPlayList) = flow {
        try {
            itemPlayList.insertPlayList(daoPlayList).also {
                emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }

    override fun getAllPlayList() = flow {
        try {
            emit(daoPlayList.getAllItemPlayList())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun deleteAllPlayList() = flow {
        try {
            daoPlayList.deleteAllItemPlaylist()
            emit(true)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(false)
        }
    }
}
