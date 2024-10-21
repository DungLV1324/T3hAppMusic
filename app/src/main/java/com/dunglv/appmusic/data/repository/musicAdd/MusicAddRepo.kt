package com.dunglv.appmusic.data.repository.musicAdd

import com.dunglv.appmusic.data.model.MusicAdd
import kotlinx.coroutines.flow.Flow

interface MusicAddRepo {
    fun getAllMusicAdd(): Flow<List<MusicAdd>>
    fun insertMusicAdd(musicAdd: MusicAdd): Flow<Long>
}