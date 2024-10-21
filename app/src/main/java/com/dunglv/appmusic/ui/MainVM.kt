package com.dunglv.appmusic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dunglv.appmusic.App
import com.dunglv.appmusic.common.MessageEvent
import com.dunglv.appmusic.data.model.Album
import com.dunglv.appmusic.data.model.Heart
import com.dunglv.appmusic.data.model.HeartAdd
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.data.model.MusicAdd
import com.dunglv.appmusic.data.model.MusicPlayList
import com.dunglv.appmusic.data.model.Recently
import com.dunglv.appmusic.data.model.RecentlyAdd
import com.dunglv.appmusic.data.model.Singer
import com.dunglv.appmusic.data.model.copyModel
import com.dunglv.appmusic.data.repository.album.AlbumRepo
import com.dunglv.appmusic.data.repository.heart.HeartRepo
import com.dunglv.appmusic.data.repository.heartadd.HeartAddRepo
import com.dunglv.appmusic.data.repository.itemplay.ItemPlayRepo
import com.dunglv.appmusic.data.repository.music.MusicRepo
import com.dunglv.appmusic.data.repository.musicAdd.MusicAddRepo
import com.dunglv.appmusic.data.repository.musicPlayList.MusicPLRepo
import com.dunglv.appmusic.data.repository.recent.RecentRepo
import com.dunglv.appmusic.data.repository.recentAdd.RecentAddRepo
import com.dunglv.appmusic.data.repository.singer.SingRepo
import com.dunglv.appmusic.ui.base.BaseViewModel
import com.dunglv.appmusic.ui.base.toMutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val musicRepository: MusicRepo,
    private val recentRepo: RecentRepo,
    private val recentAddRepo: RecentAddRepo,
    private val albumRepo: AlbumRepo,
    private val heartRepo: HeartRepo,
    private val heartAddRepo: HeartAddRepo,
    private val musicAddRepo: MusicAddRepo,
    private val musicPLRepo: MusicPLRepo,
    private val itemPlayRepo: ItemPlayRepo,
    private val singerRepo: SingRepo,
) : BaseViewModel() {

    private var _listSingLiveData: MutableLiveData<List<Singer>> = MutableLiveData()
    val listSingLiveData: MutableLiveData<List<Singer>>
        get() = _listSingLiveData

    private var _listPlayListLiveData: MutableLiveData<List<ItemPlayList>> = MutableLiveData()
    val listPlayListLiveData: LiveData<List<ItemPlayList>>
        get() = _listPlayListLiveData

    private fun getValuePlayList() = _listPlayListLiveData.toMutableList { it.copy() }


    private var _listAlbumLiveData: MutableLiveData<List<Album>> = MutableLiveData()
    val listAlbumLiveData: MutableLiveData<List<Album>>
        get() = _listAlbumLiveData

    private fun getValueAlbum() = _listAlbumLiveData.toMutableList { it.copy() }


    private val _musicPlayLiveData: MutableLiveData<Music> = MutableLiveData()
    val musicPlayLiveData: LiveData<Music>
        get() = _musicPlayLiveData


    private var _liveDataMessage: MutableLiveData<MessageEvent> = MutableLiveData()
    val liveDataMessage: LiveData<MessageEvent>
        get() = _liveDataMessage


    private var _listRecentLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listRecentLiveData: LiveData<List<Music>>
        get() = _listRecentLiveData


    private var _listHeartLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listHeartLiveData: LiveData<List<Music>>
        get() = _listHeartLiveData

    private fun getValueHeart() = _listHeartLiveData.toMutableList { it.copy() }

    private var _listMusicLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listMusicLiveData: LiveData<List<Music>>
        get() = _listMusicLiveData

    private var _listMenuAllLiveData: MutableLiveData<List<Music>> = MutableLiveData()
    val listMenuAllLiveData: LiveData<List<Music>>
        get() = _listMenuAllLiveData

    private fun getValueMenuAll() = _listMenuAllLiveData.toMutableList { it.copy() }

    private fun getValueRecently() = _listRecentLiveData.toMutableList { it.copy() }

    fun deleteAllRecently() {
        viewModelScope.launch(Dispatchers.IO) {
            getValueRecently().let { temp ->
                recentRepo.deleteAllRecently()
                    .flowOn(Dispatchers.IO)
                    .catch { it.printStackTrace() }
                    .collectLatest {
                        temp.clear()
                        _listRecentLiveData.postValue(temp)
                    }
            }
        }
    }

    fun insertRecent(uri: String) = viewModelScope.launch(Dispatchers.IO) {
        Recently(uri).apply {
            recentRepo.insertRecent(this)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    this.id = it
                    getValueRecently().let { list ->
                        getValueMusic().let { allMusic ->
                            allMusic.firstOrNull { it.uri == uri }?.let { music ->
                                list.add(music)
                                _listRecentLiveData.postValue(list)
                            }
                        }
                    }
                }
        }
    }


    fun getAllRecently() {
        viewModelScope.launch(Dispatchers.IO) {
            recentRepo.getAllMusicRecent()
                .flowOn(Dispatchers.IO)
                .collectLatest { recentLy ->
                    //mapNotNull dùng chuyển đổi từng thành phần
                    // của bộ sưu tập sang một dạng khác trong khi lọc ra bất kỳ null kết quả nào.
                    // trả về một danh sách chứa các kết quả khác null .
                    val selectedItems = recentLy.mapNotNull { path ->
                        //find dùng tìm phần tử đầu tiên trong tập hợp
                        // thỏa mãn 1 điều kiện cụ thể.
                        getValueMusic().find { item -> item.uri == path.uri }
                    }.distinctBy { it.nameSong }
                    _listRecentLiveData.postValue(selectedItems)
                }
        }
    }

    //recently add
    fun insertRecentAdd(uri: String, idPlayList: Long) = viewModelScope.launch(Dispatchers.IO) {
        recentAddRepo.insertRecentAdd(RecentlyAdd(uri, idPlayList)).flowOn(Dispatchers.IO)
            .collectLatest {}
    }

    //heart
    fun sortHeart() = viewModelScope.launch(Dispatchers.IO) {
        //let : dùng khji chưa chắc chắn đối tượng null hay không
        getValueHeart().let { temp ->
            temp.sortBy { it.nameSong }
            _listHeartLiveData.postValue(temp)
        }
    }

    fun sortHeartZA() = viewModelScope.launch(Dispatchers.IO) {
        getValueHeart().let { temp ->
            temp.sortByDescending { it.nameSong }
            _listHeartLiveData.postValue(temp)
        }
    }

    fun sortSingHeart() = viewModelScope.launch(Dispatchers.IO) {
        getValueHeart().let { temp ->
            temp.sortBy { it.nameSing }
            _listHeartLiveData.postValue(temp)
        }
    }

    fun sortHeartTime() = viewModelScope.launch(Dispatchers.IO) {
        getValueHeart().let { temp ->
            temp.sortBy { it.duration }
            _listHeartLiveData.postValue(temp)
        }
    }

    private fun deleteHeart(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            heartRepo.deleteAllHeart(uri).flowOn(Dispatchers.IO).collectLatest {
                getValueHeart().let { listHeart ->
                    val index = listHeart.indexOfFirst { it.uri == uri }
                    if (index >= 0) {
                         listHeart.removeAt(index)
                        _musicPlayLiveData.value?.let {
                            it.isHeart = false
                            _musicPlayLiveData.postValue(it)
                        }
                        _listHeartLiveData.postValue(listHeart)
                    }
                }

            }

        }
    }

    private fun insertHeart(uri: String) = viewModelScope.launch(Dispatchers.IO) {
        Heart(uri).apply {
            heartRepo.insertHeart(this)
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    this.id = it
                    _musicPlayLiveData.value?.let { music ->
                        music.isHeart = true
                        _musicPlayLiveData.postValue(music)
                        getValueHeart().let { listHeart ->
                            listHeart.add(music)
                            _listHeartLiveData.postValue(listHeart)
                        }
                    }
                }
        }

    }

    fun getAllHeart() {
        viewModelScope.launch(Dispatchers.IO) {
            getValueHeart().let { it ->
                if (it.isEmpty()) {
                    heartRepo.getAllMusicHeart()
                        .flowOn(Dispatchers.IO)
                        .collectLatest { allHeart ->
                            val selectedItems = allHeart.mapNotNull { path ->
                                getValueMusic().find { item -> item.uri == path.uri }
                            }.distinctBy { it.nameSong }
                            _listHeartLiveData.postValue(selectedItems)
                        }
                } else {
                    _listHeartLiveData.postValue(_listHeartLiveData.value)
                }
            }
        }
    }

    private fun List<Heart>.postValue() {
        val selectedItems = this.mapNotNull { path ->
            getValueMusic().find { item -> item.uri == path.uri }
        }.distinctBy { it.album }
        _listHeartLiveData.postValue(selectedItems)
    }

    private fun List<Recently>.postValueNameSong() {
        val selectedItems = this.mapNotNull { path ->
            getValueMusic().find { item -> item.uri == path.uri }
        }.distinctBy { it.nameSong }
        _listRecentLiveData.postValue(selectedItems)
    }

    //heart add
    fun insertHeartAdd(uri: String, idPlayList: Long) = viewModelScope.launch(Dispatchers.IO) {
        heartAddRepo.insertHeartAdd(HeartAdd(uri, idPlayList)).flowOn(Dispatchers.IO)
            .collectLatest {

            }
    }

    //music
    private fun getValueMusic() = _listMusicLiveData.toMutableList { it.copyModel() }

    fun getMusicWithUri(uri: String) = viewModelScope.launch(Dispatchers.IO) {
        getValueMusic().let { temp ->
            temp.firstOrNull { it.uri == uri }?.let { item ->
                getValueHeart().let { hearts ->
                    if (hearts.isEmpty()) {
                        heartRepo.getAllMusicHeart()
                            .flowOn(Dispatchers.IO)
                            .collectLatest { musicHearts ->
                                musicHearts.postValue()
                                item.isHeart = musicHearts.any { it.uri == item.uri }
                            }
                    } else {
                        item.isHeart = hearts.any { it.uri == item.uri }
                    }
                }
                _musicPlayLiveData.postValue(item)
            }
        }
    }
     fun getPreviousMusic(uri: String): Int {
        App.instance.listAllMusic.let { allMusic ->
            var currentPos = allMusic.indexOfFirst { it.uri == uri }
            if (currentPos == 0) {
                currentPos = allMusic.size - 1
            } else {
                currentPos-= 1
            }
            return currentPos
        }
    }
    fun getPreviousNextMusic(uri: String): Int {
        App.instance.listAllMusic.let { allMusic ->
            var currentPos = allMusic.indexOfFirst { it.uri == uri }
            if (currentPos == 0) {
                currentPos = allMusic.size - 1
            } else {
                currentPos+= 1
            }
            return currentPos
        }
    }
    fun insertMusicPlayList(uri: String, idPlayList: Long) = viewModelScope.launch(Dispatchers.IO) {
        musicPLRepo.insertMusicPL(MusicPlayList(uri, idPlayList)).flowOn(Dispatchers.IO)
            .collectLatest {

            }
    }

    fun initData() = viewModelScope.launch(Dispatchers.IO) {
        musicRepository.getAllMusicNoImage()
            .flowOn(Dispatchers.IO)
            .collectLatest { listNoImage ->
                listNoImage.distinctBy { it.nameSong }.let { listAll ->
                    _listMusicLiveData.postValue(listAll)
                    App.instance.listAllMusic.clear()
                    App.instance.listAllMusic.addAll(listAll)
                }
                musicRepository.getAllMusicImage()
                    .flowOn(Dispatchers.IO)
                    .collectLatest { listImage ->
                        listImage.distinctBy { it.nameSong }.let { listAll ->
                            _listMusicLiveData.postValue(listAll)
                            App.instance.listAllMusic.clear()
                            App.instance.listAllMusic.addAll(listAll)
                        }
                    }
            }
    }

    fun sortSong() = viewModelScope.launch(Dispatchers.IO) {
        getValueMusic().let { temp ->
            temp.sortBy { it.nameSong }
            _listMusicLiveData.postValue(temp)
        }
    }

    fun sortSongZA() = viewModelScope.launch(Dispatchers.IO) {
        getValueMusic().let { temp ->
            temp.sortByDescending { it.nameSong }
            _listMusicLiveData.postValue(temp)
        }
    }

    fun sortSing() = viewModelScope.launch(Dispatchers.IO) {
        getValueMusic().let { temp ->
            temp.sortBy { it.nameSing }
            _listMusicLiveData.postValue(temp)
        }
    }

    fun sortSongTime() = viewModelScope.launch(Dispatchers.IO) {
        getValueMusic().let { temp ->
            temp.sortBy { it.duration }
            _listMusicLiveData.postValue(temp)
        }
    }


    //music add
    fun insertMusicAdd(uri: String, idPlayList: Long) = viewModelScope.launch(Dispatchers.IO) {
        musicAddRepo.insertMusicAdd(MusicAdd(uri, idPlayList)).flowOn(Dispatchers.IO)
            .collectLatest {

            }
    }

    //album
    fun sortAlbum() = viewModelScope.launch(Dispatchers.IO) {
        getValueAlbum().let { temp ->
            temp.sortBy { it.name }
            _listAlbumLiveData.postValue(temp)
        }
    }

    fun sortSingAlbum() = viewModelScope.launch(Dispatchers.IO) {
        getValueAlbum().let { temp ->
            temp.sortBy { it.nameSing }
            _listAlbumLiveData.postValue(temp)
        }
    }

    fun sortAlbumZA() = viewModelScope.launch(Dispatchers.IO) {
        getValueAlbum().let { temp ->
            temp.sortByDescending { it.name }
            _listAlbumLiveData.postValue(temp)
        }
    }


    fun initAlbum() =
        viewModelScope.launch(Dispatchers.IO) {
            albumRepo.getAllAlbum().flowOn(Dispatchers.IO)
                .collectLatest { album ->
                    _listAlbumLiveData.postValue(album.distinctBy { it.name })
                }
        }

    //sing
    fun getAllSinger() = viewModelScope.launch(Dispatchers.IO) {
        singerRepo.getAllSinger().flowOn(Dispatchers.IO)
            .collectLatest { sing ->
                _listSingLiveData.postValue(sing.distinctBy { it.nameSinger })
            }
    }

    //play list
    fun checkHeartMusicPlayer() =
        _musicPlayLiveData.value?.let {
            if (!it.isHeart) {
                it.uri?.let { it1 -> insertHeart(it1) }
            } else {
                it.uri?.let { it1 -> deleteHeart(it1) }
            }
        }

    fun showListItemPlay() =
        viewModelScope.launch(Dispatchers.IO) {
            val messageEvent = MessageEvent(0, getValuePlayList())
            _liveDataMessage.postValue(messageEvent)
        }

    fun getAllPlayList() = viewModelScope.launch(Dispatchers.IO) {
        itemPlayRepo.getAllPlayList().flowOn(Dispatchers.IO)
            .collectLatest { playList ->
                _listPlayListLiveData.postValue(playList)
            }
    }


    fun insertItemPlayList(name: String) =
        viewModelScope.launch(Dispatchers.IO) {
            getValuePlayList().let { temp ->
                ItemPlayList(name = name).apply {
                    itemPlayRepo.insertItemPlay(this).flowOn(Dispatchers.IO)
                        .collectLatest {
                            this.id = it
                            temp.add(this)
                            _listPlayListLiveData.postValue(temp)
                        }
                }
            }
        }
    fun deleteAllPlayList() {
        viewModelScope.launch(Dispatchers.IO) {
            getValuePlayList().let { temp ->
                itemPlayRepo.deleteAllPlayList()
                    .flowOn(Dispatchers.IO)
                    .catch { it.printStackTrace() }
                    .collectLatest {
                        temp.clear()
                        _listPlayListLiveData.postValue(temp)
                    }
            }
        }
    }

    private var _isExistPlayListMutableLiveData: MutableLiveData<Boolean> =
        MutableLiveData()
    val isExistPlayListLiveData: LiveData<Boolean>
        get() = _isExistPlayListMutableLiveData

    fun isExistPlayList(text: String) = viewModelScope.launch(Dispatchers.IO) {
        getValuePlayList().any { it.name == text }.let { isExist ->
            _isExistPlayListMutableLiveData.postValue(isExist)
        }
    }

    fun postNullExist() = _isExistPlayListMutableLiveData.postValue(null)
    var currentPlayList: Long = -1
    var checkPermission: Boolean = false
}





