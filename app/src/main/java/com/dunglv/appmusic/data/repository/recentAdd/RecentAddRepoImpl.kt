package com.dunglv.appmusic.data.repository.recentAdd

import com.dunglv.appmusic.data.local.room.dao.DaoRecentlyAdd
import com.dunglv.appmusic.data.model.RecentlyAdd
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecentAddRepoImpl @Inject constructor(
    private val daoRecentlyAdd: DaoRecentlyAdd
) : RecentAddRepo {
    override fun getAllMusicRecent() = flow {
        try {
            emit(daoRecentlyAdd.getAllMusicAddRecently())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }


    override fun insertRecentAdd(recentlyAdd: RecentlyAdd) = flow {
        try {
            emit(recentlyAdd.insertRecentlyAdd(daoRecentlyAdd))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }
}