package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.RecentlyAdd

@Dao
interface DaoRecentlyAdd {
    @Insert
    fun insertAddRecently (musicAdd: RecentlyAdd):Long

    @Update
    fun update(musicAdd: RecentlyAdd)

    @Query("DELETE FROM RecentlyAdd WHERE uri=:uri")
    fun deleteAddRecentlyUri (uri: String)

    @Query("SELECT * FROM recentlyAdd")
    fun getAllMusicAddRecently (): List<RecentlyAdd>
}