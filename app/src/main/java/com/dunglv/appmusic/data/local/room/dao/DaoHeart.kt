package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.Heart
import com.dunglv.appmusic.data.model.Music

@Dao
interface DaoHeart {
    @Insert
    fun insert(heart: Heart) :Long
    @Update
    fun update(heart: Heart)
    @Query("DELETE FROM Heart WHERE uri=:uri")
    fun deleteHeart(uri: String)
    @Query("SELECT * FROM heart")
    fun getAllMusic(): List<Heart>
}