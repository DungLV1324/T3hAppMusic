package com.dunglv.appmusic.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dunglv.appmusic.data.model.MusicPlayList

@Dao
interface DaoMusicPL {
    @Insert
    fun insertPlayList(musicPlayList: MusicPlayList): Long

    @Query("SELECT * FROM musicPlayList")
    fun getAllMusicPlayList(): List<MusicPlayList>
}