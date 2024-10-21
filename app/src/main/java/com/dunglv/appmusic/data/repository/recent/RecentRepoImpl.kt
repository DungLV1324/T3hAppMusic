package com.dunglv.appmusic.data.repository.recent

import com.dunglv.appmusic.data.local.room.dao.DaoRecently
import com.dunglv.appmusic.data.model.Recently
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecentRepoImpl @Inject constructor(private val daoRecently: DaoRecently) : RecentRepo {
    override fun getAllMusicRecent() = flow {
        try {
            emit(daoRecently.getAllMusicRecently())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }

    override fun insertRecent(recently: Recently) = flow {
        try {
            emit(recently.insertRecently(daoRecently))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }

    override fun deleteAllRecently() = flow {
        try {
            daoRecently.deleteAllRecent()
            emit(true)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(false)
        }
    }
}
