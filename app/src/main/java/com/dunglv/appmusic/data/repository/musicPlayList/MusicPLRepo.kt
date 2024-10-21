package com.dunglv.appmusic.data.repository.musicPlayList

import com.dunglv.appmusic.data.model.MusicPlayList
import kotlinx.coroutines.flow.Flow

interface MusicPLRepo {
    fun getAllMusicPL(): Flow<List<MusicPlayList>>
    fun insertMusicPL(musicPlayList: MusicPlayList): Flow<Long>
}