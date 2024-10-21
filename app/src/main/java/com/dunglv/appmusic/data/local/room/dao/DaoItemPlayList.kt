package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dunglv.appmusic.data.model.ItemPlayList

@Dao
interface DaoItemPlayList {
    @Insert
    fun insertPlayList(itemPlayList: ItemPlayList): Long

    @Query("SELECT * FROM itemplaylist")
    fun getAllItemPlayList(): List<ItemPlayList>

    @Query("DELETE FROM itemplaylist ")
    fun deleteAllItemPlaylist()
}