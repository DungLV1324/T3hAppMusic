package com.dunglv.appmusic.ui.home.singer

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Singer
import com.dunglv.appmusic.ui.adapter.SingAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentSingerBinding

class SingerFragment : BaseBindingFragment<FragmentSingerBinding, SingerViewModel>() {

    private val singAdapter: SingAdapter by lazy {
        SingAdapter().apply {
            iClickSongPlay = { _, it -> initSwitchScreenAllSing(it) }
        }
    }

    override fun getViewModel() = SingerViewModel::class.java

    override val layoutId = R.layout.fragment_singer

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun initAdapter() {
        binding.rcSing.adapter = singAdapter
    }

    private fun onClick() {
        binding.imPlay.setOnSafeClick {
            mainVM.listMusicLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun initSwitchScreenAllSing(singer: Singer) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_NAME, singer.nameSinger)
                navigateBundle(R.id.fragment_sing_belong_to_song, this)
            }
        }.onFailure { it.printStackTrace() }
    }

    private fun initData() {
        with(mainVM) {
            listSingLiveData.value.let { listSinger ->
                if (listSinger.isNullOrEmpty())
                    getAllSinger()
            }
            listSingLiveData.observeWithCatch(viewLifecycleOwner) {
                it?.let { data -> singAdapter.submitList(data) }
            }
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