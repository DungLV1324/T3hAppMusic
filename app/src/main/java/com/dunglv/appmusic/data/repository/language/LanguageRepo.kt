package com.dunglv.appmusic.data.repository.language

import com.dunglv.appmusic.data.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepo {
    fun getAllLanguage(): Flow<List<Language>>
}