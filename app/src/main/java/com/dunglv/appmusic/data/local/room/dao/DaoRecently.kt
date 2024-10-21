package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.Recently

@Dao
interface DaoRecently {
    @Insert
    fun insertRecently(recently: Recently):Long
    @Update
    fun updateRecently(recently: Recently)
    @Query("DELETE FROM Recently WHERE uri =:uri")
    fun deleteUri (uri: String)
    @Query("DELETE FROM recently ")
    fun deleteAllRecent()
    @Query("SELECT * FROM recently")
    fun getAllMusicRecently(): List<Recently>
}