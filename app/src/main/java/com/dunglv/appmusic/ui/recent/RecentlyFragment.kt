package com.dunglv.appmusic.ui.recent

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.RecentlyAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetDialogMenuSong
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentRecentlyBinding

class RecentlyFragment : BaseBindingFragment<FragmentRecentlyBinding, RecentlyViewModel>() {
    private val bottomSheetMenuSong: BottomSheetDialogMenuSong by lazy { BottomSheetDialogMenuSong() }

    private val recentlyAdapter: RecentlyAdapter by lazy {
        RecentlyAdapter().apply {
            iClickDelete = { _, music -> showMenu(music) }
            iClickSongPlay = { _, music -> navigateMusic(music.uri) }
        }
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun getViewModel() = RecentlyViewModel::class.java

    override val layoutId = R.layout.fragment_recently

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun initAdapter() {
        binding.rcRecently.adapter = recentlyAdapter
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }

        binding.imDelete.setOnSafeClick {
            mainVM.deleteAllRecently()
        }

        binding.tvPlayRandom.setOnSafeClick {
            mainVM.listRecentLiveData.value?.let { all ->
                navigateMusic(all.random().uri)
            }
        }
    }

    private fun initData() {
        mainVM.listRecentLiveData.let {
            if (it.value.isNullOrEmpty()) {
                mainVM.getAllRecently()
            }
            it.observeWithCatch(viewLifecycleOwner) { data ->
                data.checkSizeMusic()
                recentlyAdapter.submitList(data)
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

    private fun navigateMusic(uri: String?) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_URI_MUSIC_CURRENT, uri)
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.imCheckData.visible()
            binding.imDelete.invisible()
            binding.tvPlayRandom.invisible()
        } else {
            binding.imCheckData.invisible()
            binding.imDelete.visible()
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