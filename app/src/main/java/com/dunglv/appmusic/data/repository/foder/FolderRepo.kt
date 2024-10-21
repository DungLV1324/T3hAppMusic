package com.dunglv.appmusic.data.repository.foder

import com.dunglv.appmusic.data.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepo {
    fun getAllFolder(): Flow<List<Folder>>
}