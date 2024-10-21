package com.dunglv.appmusic.data.repository.heart

import com.dunglv.appmusic.data.model.Heart
import kotlinx.coroutines.flow.Flow

interface HeartRepo {
    fun getAllMusicHeart(): Flow<List<Heart>>
    fun insertHeart(heart: Heart): Flow<Long>
    fun deleteAllHeart(uri: String): Flow<String>
}