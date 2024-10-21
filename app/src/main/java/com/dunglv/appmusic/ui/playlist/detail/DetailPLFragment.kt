package com.dunglv.appmusic.ui.playlist.detail

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.DetailPlayListAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.shareMusic
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentDetailPlayListBinding

class DetailPLFragment :
    BaseBindingFragment<FragmentDetailPlayListBinding, DetailPlayListViewModel>() {
    private val detailPlayListAdapter: DetailPlayListAdapter by lazy {
        DetailPlayListAdapter().apply {
            iClickSongPlay = { _, music -> navigatePlayMusic(music.uri) }
            iClickMenu = { _, music -> context?.shareMusic(music.uri)  }
        }
    }

    override fun getViewModel(): Class<DetailPlayListViewModel> {
        return DetailPlayListViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_detail_play_list

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        onClick()
        initArguments()
    }

    private fun initAdapter() {
        binding.rcPlaylist.adapter = detailPlayListAdapter
    }


    private fun initArguments() {
        arguments?.let { it1 ->
            val name =
                it1.getString(Constant.KEY_NAME) ?: it1.getString(Constant.KEY_PLAY_LIST) ?: ""
            binding.tvTitle.text = name
            initData()
        }

    }

    private fun initData() {
        arguments?.let {
            it.getLong(Constant.PLAYLIST).let { id ->
                mainVM.listMusicLiveData.value?.let { music ->
                    viewModel.getAllDetailPlayList(id, music)
                }
            }
        }
        with(viewModel) {
            listDetailPlayList.observeWithCatch(viewLifecycleOwner) {
                it.checkSizeMusic()
                detailPlayListAdapter.submitList(it)

            }
        }
    }

    private fun navigatePlayMusic(uri: String?) {
        runCatching {
            Bundle().apply {
                uri?.let { mainVM.insertRecent(it) }
                putString(Constant.KEY_URI_MUSIC_CURRENT, uri)
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }
        binding.imAdd.setOnClickListener {
            navigateFragment(R.id.fragment_add)
        }
        binding.tvAdd.setOnClickListener {
            navigateFragment(R.id.fragment_add)
        }
        binding.imPlay.setOnSafeClick {
            viewModel.listDetailPlayList.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.imPlay.invisible()
            binding.tvName.invisible()
            binding.tvAdd.visible()
        } else {
            binding.imPlay.visible()
            binding.tvName.visible()
            binding.tvAdd.invisible()

        }
    }
}