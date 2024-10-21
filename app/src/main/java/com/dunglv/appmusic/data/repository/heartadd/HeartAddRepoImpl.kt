package com.dunglv.appmusic.data.repository.heartadd

import com.dunglv.appmusic.data.local.room.dao.DaoHeartAdd
import com.dunglv.appmusic.data.model.HeartAdd
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeartAddRepoImpl @Inject constructor(private val daoHeartAdd: DaoHeartAdd) : HeartAddRepo {
    override fun insertHeartAdd(heartAdd: HeartAdd) = flow {
        try {
            heartAdd.insertHeartAdd(daoHeartAdd).also {
                emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(-1)
        }
    }

    override fun getAllHeartAdd() = flow {
        try {
            emit(daoHeartAdd.getAllAddHeart())
        } catch (e: Exception) {
            e.printStackTrace()
            emit(mutableListOf())
        }
    }
}
