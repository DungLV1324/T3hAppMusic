package com.dunglv.appmusic.ui.home.folder.folderdetailmusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FolderDetailViewModel : BaseViewModel() {
    private var _listFolderDetailLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listFolderDetailLiveData: LiveData<List<Music>>
        get() = _listFolderDetailLiveData

    fun checkFolder(data: List<Music>, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            data.let { music ->
                music.filter { it.nameFolder == name }.apply {
                    _listFolderDetailLiveData.postValue(this)
                }
            }
        }
    }
}