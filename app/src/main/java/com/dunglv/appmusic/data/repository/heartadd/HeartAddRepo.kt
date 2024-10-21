package com.dunglv.appmusic.data.repository.heartadd

import com.dunglv.appmusic.data.model.HeartAdd
import kotlinx.coroutines.flow.Flow

interface HeartAddRepo {
    fun insertHeartAdd(heartAdd: HeartAdd): Flow<Long>
    fun getAllHeartAdd(): Flow<List<HeartAdd>>
}