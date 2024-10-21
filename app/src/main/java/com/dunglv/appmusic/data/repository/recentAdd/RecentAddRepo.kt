package com.dunglv.appmusic.data.repository.recentAdd

import com.dunglv.appmusic.data.model.RecentlyAdd
import kotlinx.coroutines.flow.Flow

interface RecentAddRepo {
    fun getAllMusicRecent(): Flow<List<RecentlyAdd>>
    fun insertRecentAdd(recentlyAdd: RecentlyAdd): Flow<Long>
}