package com.dunglv.appmusic.ui.home.singer.singdetailmusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingDetailViewModel @Inject constructor() : BaseViewModel() {

    private var _listSingDetailLiveData: MutableLiveData<List<Music>> = MutableLiveData()

    val listSingDetailLiveData: LiveData<List<Music>>
        get() = _listSingDetailLiveData

    fun checkSing(data: List<Music>, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.let { music ->
                music.filter { it.nameSing == name }.apply {
                    _listSingDetailLiveData.postValue(this)
                }
            }
        }
    }
}