package com.dunglv.appmusic.data.repository.recent

import com.dunglv.appmusic.data.model.Recently
import kotlinx.coroutines.flow.Flow

interface RecentRepo {
    fun getAllMusicRecent(): Flow<List<Recently>>
    fun insertRecent(recently: Recently): Flow<Long>
    fun deleteAllRecently(): Flow<Boolean>
}