package com.dunglv.appmusic.ui.setting.language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Language
import com.dunglv.appmusic.data.repository.language.LanguageRepo
import com.dunglv.appmusic.ui.base.BaseViewModel
import com.dunglv.appmusic.ui.base.toMutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val languageRepo: LanguageRepo):BaseViewModel() {
    private var _listLanguageLiveData: MutableLiveData<List<Language>> = MutableLiveData()
    val listLanguageLiveData: MutableLiveData<List<Language>>
        get() = _listLanguageLiveData

    private fun getValueLanguage() = _listLanguageLiveData.toMutableList { it.copy() }

    fun getAllLanguage() = viewModelScope.launch(Dispatchers.IO) {
        languageRepo.getAllLanguage().flowOn(Dispatchers.IO).collectLatest { laguage ->
            _listLanguageLiveData.postValue(laguage)
        }
    }

    fun updateSelectOneView(pos: Int) = viewModelScope.launch(Dispatchers.IO) {
        getValueLanguage().let { temp ->
            temp.let {
                for (i in it.indices) {
                    it[i].isSelect = (i == pos)
                    _listLanguageLiveData.postValue(it)
                }
            }
        }
    }
}