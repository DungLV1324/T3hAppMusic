package com.dunglv.appmusic.data.repository.music

import com.dunglv.appmusic.data.model.Music
import kotlinx.coroutines.flow.Flow

interface MusicRepo {
    fun insertMusic(music: Music): Flow<Long>
    fun getAllMusicNoImage(): Flow<List<Music>>
    fun getAllMusicImage(): Flow<List<Music>>
}