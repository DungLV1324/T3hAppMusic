package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.HeartAdd
import com.dunglv.appmusic.data.model.RecentlyAdd

@Dao
interface DaoHeartAdd {
    @Insert
    fun insertAddHeart(heart: HeartAdd):Long

    @Update
    fun update(musicAdd: RecentlyAdd)

    @Query("DELETE FROM HeartAdd WHERE uri=:uri")
    fun deleteHearUri(uri: String)

    //Get all Music
    @Query("SELECT * FROM HeartAdd")
    fun getAllAddHeart(): List<HeartAdd>
}