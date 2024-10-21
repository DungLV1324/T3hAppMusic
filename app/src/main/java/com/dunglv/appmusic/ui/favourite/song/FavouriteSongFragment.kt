package com.dunglv.appmusic.ui.favourite.song

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.adapter.FavouriteSongAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetDialogMenuSong
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetHeartArrangeDialog
import com.dunglv.appmusic.utils.extention.gone
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentFavouriteSongBinding

class FavouriteSongFragment
    : BaseBindingFragment<FragmentFavouriteSongBinding, FavouriteSongViewModel>() {
    private lateinit var sharedPreferences: SharedPreferences
    private val bottomSheetMenuSong: BottomSheetDialogMenuSong by lazy { BottomSheetDialogMenuSong() }

    private val favouriteSongAdapter: FavouriteSongAdapter by lazy {
        FavouriteSongAdapter().apply {
            iClickSongPlay = { _, music -> navigatePlayMusic(music.uri) }
            iClickMenu = { _, music -> showMenu(music) }
        }
    }

    private val bottomSheetArrangeDialog: BottomSheetHeartArrangeDialog by lazy {
        BottomSheetHeartArrangeDialog().apply {
            iClickFavourSong = { mainVM.sortHeart() }
            iClickFavourSongZA = { mainVM.sortHeartZA() }
            iClickFavourSing = { mainVM.sortSingHeart() }
            iClickFavourTime = { mainVM.sortHeartTime() }
        }
    }

    override fun getViewModel() = FavouriteSongViewModel::class.java

    override val layoutId = R.layout.fragment_favourite_song

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onCLick()
    }

    private fun initData() {
        mainVM.getAllHeart()
        mainVM.listHeartLiveData.observeWithCatch(viewLifecycleOwner) { song ->
            song.let { data ->
                song.checkSizeMusic()
                favouriteSongAdapter.submitList(data)
            }
        }
    }

    private fun initAdapter() {
        binding.rcFavouriteSong.adapter = favouriteSongAdapter
    }

    private fun onCLick() {
        binding.imArrange.setOnSafeClick { showArrangeDialog() }
        binding.imPlay.setOnSafeClick { ranDomMusic() }
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

    private fun ranDomMusic() = mainVM.listHeartLiveData.value?.let { all ->
            navigatePlayMusic(all.random().uri)
    }

    private fun showArrangeDialog() = bottomSheetArrangeDialog.show(
        childFragmentManager.beginTransaction().remove(bottomSheetArrangeDialog), tag
    )

    private fun showMenu(music: Music) {
        runCatching {
            bottomSheetMenuSong.music = music
            bottomSheetMenuSong.show(childFragmentManager, tag)
            music.uri?.let { saveTokenUri(it) }
        }.onFailure { it.printStackTrace() }
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.csView.gone()
            binding.tvCheckData.visible()
        } else {
            binding.csView.visible()
            binding.tvCheckData.gone()
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