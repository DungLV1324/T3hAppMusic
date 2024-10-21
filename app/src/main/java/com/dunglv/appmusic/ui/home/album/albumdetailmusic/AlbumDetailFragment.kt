package com.dunglv.appmusic.ui.home.album.albumdetailmusic

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.AlbumDetailAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetDialogMenuSong
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.shareMusic
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentAlbumBelongToSongBinding

class AlbumDetailFragment :
    BaseBindingFragment<FragmentAlbumBelongToSongBinding, AlbumDetailViewModel>() {
    private val bottomSheetMenuSong: BottomSheetDialogMenuSong by lazy { BottomSheetDialogMenuSong() }
    private val albumDetailAdapter: AlbumDetailAdapter by lazy {
        AlbumDetailAdapter().apply {
            iClickMenu = { _, music -> showMenu(music) }
            iClickSongPlay = { _, it -> navigatePlayMusic(it.uri) }
            iClickSearch = { _, music -> context?.shareMusic(music.uri) }
        }
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun getViewModel() = AlbumDetailViewModel::class.java

    override val layoutId = R.layout.fragment_album_belong_to_song

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun initAdapter() {
        binding.rcSongInMusic.adapter = albumDetailAdapter
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }

        binding.tvPlayRandom.setOnSafeClick {
            viewModel.listAlbumDetailLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun initData() {
        arguments?.let { it1 ->
            it1.getString(Constant.KEY_NAME)?.let { name ->
                with(mainVM) {
                    binding.tvTitle.text = name
                    listMusicLiveData.value?.let {
                        viewModel.checkAlbum(it, name)

                    }
                }
                viewModel.listAlbumDetailLiveData.observeWithCatch(viewLifecycleOwner) { allSong ->
                    allSong.let { data ->
                        data.checkSizeMusic()
                        albumDetailAdapter.submitList(data.distinctBy { it.nameSong })
                    }
                }
            }
        }
    }

    private fun showMenu(music: Music) {
        runCatching {
            bottomSheetMenuSong.music = music
            bottomSheetMenuSong.show(childFragmentManager, tag)
            music.uri?.let { saveTokenUri(it) }
        }.onFailure { it.printStackTrace() }
    }

    private fun navigatePlayMusic(uri: String?) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_URI_MUSIC_CURRENT, uri)
                uri?.let { mainVM.insertRecent(it) }
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }

    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.imCheckData.visible()
            binding.tvPlayRandom.invisible()
        } else {
            binding.imCheckData.invisible()
            binding.tvPlayRandom.visible()

        }
    }

    private fun saveTokenUri(type: String) {
        sharedPreferences =
            activity!!.getSharedPreferences(Constant.KEY_URI_PLAY_LIST, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putString(Constant.KEY_URI, type)
        editor?.apply()
    }
}

