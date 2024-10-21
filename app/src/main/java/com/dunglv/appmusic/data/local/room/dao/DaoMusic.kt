package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.Music

@Dao
interface DaoMusic {
    @Insert
    fun insert(music: Music) :Long
    @Update
    fun update(music: Music)
    @Query("DELETE FROM Music WHERE nameSong=:nameSong")
    fun deleteMusic(nameSong: String)
    //Get all Music
    @Query("SELECT * FROM music")
    fun getAllMusic(): List<Music>
}