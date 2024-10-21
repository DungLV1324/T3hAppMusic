package com.dunglv.appmusic.data.repository.singer

import com.dunglv.appmusic.data.model.Singer
import kotlinx.coroutines.flow.Flow

interface SingRepo {
    fun getAllSinger(): Flow<List<Singer>>
}