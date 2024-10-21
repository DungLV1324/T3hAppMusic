package com.dunglv.appmusic.ui.playlist.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.data.repository.heartadd.HeartAddRepo
import com.dunglv.appmusic.data.repository.musicAdd.MusicAddRepo
import com.dunglv.appmusic.data.repository.musicPlayList.MusicPLRepo
import com.dunglv.appmusic.data.repository.recentAdd.RecentAddRepo
import com.dunglv.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlayListViewModel @Inject constructor(
    private val musicAddRepo: MusicAddRepo,
    private val musicPLRepo: MusicPLRepo,
    private val recentAddRepo: RecentAddRepo,
    private val heartAddRepo: HeartAddRepo
) : BaseViewModel() {


    private var _listDetailPlayList: MutableLiveData<List<Music>> = MutableLiveData()
    val listDetailPlayList: MutableLiveData<List<Music>>
        get() = _listDetailPlayList

    fun getAllDetailPlayList(idPlayList: Long, data: List<Music>) =
        viewModelScope.launch(Dispatchers.IO) {
            val allDetail: MutableList<Music> = mutableListOf()
            val recent = checkRecentAdd(allDetail, data, idPlayList)
            val heart = checkHeartAdd(allDetail, data, idPlayList)
            val musicPlay = checkMusicPlayList(allDetail, data, idPlayList)
            val musicAdd = checkMusicAdd(allDetail, data, idPlayList)
            listOf(recent, heart, musicAdd, musicPlay).awaitAll()
            _listDetailPlayList.postValue(allDetail.distinctBy { it.nameSong })
        }

    private fun checkRecentAdd(music: MutableList<Music>, data: List<Music>, idPlayList: Long) =
        viewModelScope.async(Dispatchers.IO) {
            recentAddRepo.getAllMusicRecent().flowOn(Dispatchers.IO)
                .collectLatest { recentAdd ->
                    recentAdd.filter { it.idPlayList == idPlayList }.let {
                        music.addAll(it.mapNotNull { path ->
                            data.find { item -> item.uri == path.uri }
                        })
                    }
                }
        }

    private fun checkMusicAdd(music: MutableList<Music>, data: List<Music>, idPlayList: Long) =
        viewModelScope.async(Dispatchers.IO) {
            musicAddRepo.getAllMusicAdd()
                .flowOn(Dispatchers.IO)
                .collectLatest { musicAdd ->
                    musicAdd.filter { it.idPlayList == idPlayList }.let {
                        music.addAll(it.mapNotNull { path ->
                            data.find { item -> item.uri == path.uri }
                        })
                    }
                }
        }

    private fun checkHeartAdd(music: MutableList<Music>, data: List<Music>, idPlayList: Long) =
        viewModelScope.async(Dispatchers.IO) {
            heartAddRepo.getAllHeartAdd().flowOn(Dispatchers.IO)
                .collectLatest { heartAdd ->
                    heartAdd.filter { it.idPlayList == idPlayList }.let {
                        music.addAll(it.mapNotNull { path ->
                            data.find { item -> item.uri == path.uri }
                        })
                    }
                }
        }

    private fun checkMusicPlayList(music: MutableList<Music>, data: List<Music>, idPlayList: Long) =
        viewModelScope.async(Dispatchers.IO) {
            musicPLRepo.getAllMusicPL().flowOn(Dispatchers.IO)
                .collectLatest { musicPlay ->
                    musicPlay.filter { it.idPlayList == idPlayList }.let {
                        music.addAll(it.mapNotNull { path ->
                            data.find { item -> item.uri == path.uri }
                        })
                    }
                }
        }
}