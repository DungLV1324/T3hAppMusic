package com.dunglv.appmusic.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseViewModel

class SearchViewModel : BaseViewModel() {
    private var _listSearchLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listSearchLiveData: LiveData<List<Music>>
        get() = _listSearchLiveData

    fun filterList(text: String, data: List<Music>) {
        data.filter { it.nameSong.uppercase().contains(text.uppercase()) }.let {
            _listSearchLiveData.postValue(it)
        }
    }

    fun posNull(){
        _listSearchLiveData.postValue(mutableListOf())
    }
}