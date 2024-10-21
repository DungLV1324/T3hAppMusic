package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dunglv.appmusic.data.model.MusicAdd

@Dao
interface DaoMusicAdd {
    @Insert
    fun insertAdd(musicAdd: MusicAdd):Long

    @Update
    fun update(musicAdd: MusicAdd)

    @Query("DELETE FROM MusicAdd WHERE uri=:uri")
    fun deleteMusicAddUri(uri: String)

    @Query("SELECT * FROM musicAdd")
    fun getAllMusicAdd(): List<MusicAdd>
}