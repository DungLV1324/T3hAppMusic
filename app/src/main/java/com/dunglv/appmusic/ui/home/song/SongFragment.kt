package com.dunglv.appmusic.ui.home.song

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.SongAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetArrangeDialog
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetDialogMenuSong
import com.dunglv.appmusic.utils.extention.gone
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.shareMusic
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentSongBinding

class SongFragment : BaseBindingFragment<FragmentSongBinding, MainVM>() {
    private lateinit var sharedPreferences: SharedPreferences

    private val bottomSheetMenuSong: BottomSheetDialogMenuSong by lazy { BottomSheetDialogMenuSong() }
    private val bottomSheetArrangeDialog: BottomSheetArrangeDialog by lazy {
        BottomSheetArrangeDialog().apply {
            iClickAZ = { mainVM.sortSong() }
            iClickZA = { mainVM.sortSongZA() }
            iClickNameSing = { mainVM.sortSing() }
            iClickTime = { mainVM.sortSongTime() }
        }
    }

    private val songAdapter: SongAdapter by lazy {
        SongAdapter().apply {
            iClickMenu = { _, music -> showMenuSongBottomSheet(music)}
            iClickSongPlay = { _, music -> navigatePlayMusic(music.uri) }
            iClickSearch = { _, music -> context?.shareMusic(music.uri) }
        }
    }

    override fun getViewModel() = MainVM::class.java

    override val layoutId = R.layout.fragment_song

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun initAdapter() {
        binding.rcSong.adapter = songAdapter
    }

    private fun onClick() {
        binding.imArrange.setOnSafeClick {
            showArrangeBottomSheet()
        }

        binding.imPlay.setOnSafeClick {
            mainVM.listMusicLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun initData() {
        mainVM.listMusicLiveData.observeWithCatch(viewLifecycleOwner) { data ->
            //viewLifecycleOwner ?
            data.checkSizeMusic()
            songAdapter.submitList(data) {
                runCatching {
                    binding.animationView.gone()
                }
            }
        }
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.animationView.visible()
            binding.rcSong.gone()
        } else {
            binding.animationView.gone()
            binding.rcSong.visible()
        }
    }

    private fun showArrangeBottomSheet() {
        runCatching {
            bottomSheetArrangeDialog.show(childFragmentManager, "")
        }.onFailure { it.printStackTrace() }
    }

    private fun showMenuSongBottomSheet(music: Music) {
        runCatching {
            bottomSheetMenuSong.music = music
            bottomSheetMenuSong.show(childFragmentManager, tag)
            music.uri?.let { saveTokenUri(it) }
        }.onFailure { it.printStackTrace() }
    }

    private fun navigatePlayMusic(uri: String?) =
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_URI_MUSIC_CURRENT, uri)
                uri?.let { mainVM.insertRecent(it) }
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }
    private fun saveTokenUri(type: String) {
        sharedPreferences =
            activity!!.getSharedPreferences(Constant.KEY_URI_PLAY_LIST, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putString(Constant.KEY_URI, type)
        editor?.apply()
    }
}



