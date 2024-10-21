package com.dunglv.appmusic.ui.home.folder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Folder
import com.dunglv.appmusic.data.repository.foder.FolderRepo
import com.dunglv.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(private val folderRepo: FolderRepo) : BaseViewModel() {

    private var _listFolderLiveData: MutableLiveData<MutableList<Folder>> = MutableLiveData()
    val listFolderLiveData: MutableLiveData<MutableList<Folder>>
        get() = _listFolderLiveData

    fun initFolder() = viewModelScope.launch(Dispatchers.IO) {
        folderRepo.getAllFolder()
            .flowOn(Dispatchers.IO)
            .collectLatest { folder ->
                _listFolderLiveData.postValue(folder.distinctBy { it.nameFolder }.toMutableList())
            }
    }
}