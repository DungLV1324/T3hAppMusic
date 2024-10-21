package com.dunglv.appmusic.ui.playlist

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.PlayListAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetAddPlayList
import com.dunglv.appmusic.utils.extention.gone
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentPlaylistBinding

class PlayListFragment : BaseBindingFragment<FragmentPlaylistBinding, MainVM>() {
    private val playListAdapter: PlayListAdapter by lazy {
        PlayListAdapter().apply {
            iClickSongPlay = { _, item ->
                navigateDetailPlayList(item.name, item.id)
            }
        }
    }

    private val bottomSheetAddPlayList: BottomSheetAddPlayList by lazy {
        BottomSheetAddPlayList().apply {
            iClickAdd = { addItem(it) }
        }
    }

    override val layoutId = R.layout.fragment_playlist

    override fun getViewModel() = MainVM::class.java

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        onClick()
        initData()
    }

    private fun initAdapter() {
        binding.rcSong.adapter = playListAdapter
    }

    private fun initData() {
        with(mainVM) {
            if (listPlayListLiveData.value.isNullOrEmpty()) {
                getAllPlayList()
            }
            listPlayListLiveData.observeWithCatch(viewLifecycleOwner) { data ->
                data.checkSize()
                playListAdapter.submitList(data)
            }
        }
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }

        binding.tvAdd.setOnSafeClick {
            bottomSheetAddPlayList.show(childFragmentManager, tag)
        }

        binding.imAdd.setOnSafeClick {
            bottomSheetAddPlayList.show(childFragmentManager, tag)
        }

        binding.imDelete.setOnSafeClick {
            mainVM.deleteAllPlayList()
        }

        binding.tvPlayRandom.setOnSafeClick {
            mainVM.listMusicLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun addItem(name: String) {
        mainVM.insertItemPlayList(name)
    }

    private fun navigateDetailPlayList(name: String, id: Long) {
        runCatching {
            mainVM.currentPlayList = id
            Bundle().apply {
                putString(Constant.KEY_NAME, name)
                putLong(Constant.PLAYLIST, id)
                mainVM.showListItemPlay()
                navigateBundle(R.id.fragment_detail_play_list, this)
            }
        }.onFailure { it.printStackTrace() }

    }

    private fun List<ItemPlayList>.checkSize() {
        if (this.isEmpty()) {
            binding.tvAdd.visible()
            binding.rcSong.gone()
            binding.imDelete.gone()
        } else {
            binding.tvAdd.invisible()
            binding.imDelete.visible()
            binding.rcSong.visible()
        }
    }

    private fun navigatePlayMusic(uri: String?) =
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_URI_MUSIC_CURRENT, uri)
                uri?.let { mainVM.insertRecent(it) }
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }
}