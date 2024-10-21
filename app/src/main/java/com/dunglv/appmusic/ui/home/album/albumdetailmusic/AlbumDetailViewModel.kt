package com.dunglv.appmusic.ui.home.album.albumdetailmusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumDetailViewModel : BaseViewModel() {
    private var _listAlbumDetailLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listAlbumDetailLiveData: LiveData<List<Music>>
        get() = _listAlbumDetailLiveData

    fun checkAlbum(data: List<Music>, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.let { music ->
                music.filter { it.album == name }.apply {
                    _listAlbumDetailLiveData.postValue(this)
                }
            }
        }
    }
}