package com.dunglv.appmusic.data.repository.itemplay

import com.dunglv.appmusic.data.model.ItemPlayList
import kotlinx.coroutines.flow.Flow

interface ItemPlayRepo {
    fun insertItemPlay(itemPlayList: ItemPlayList): Flow<Long>
    fun getAllPlayList(): Flow<List<ItemPlayList>>
    fun deleteAllPlayList(): Flow<Boolean>
}