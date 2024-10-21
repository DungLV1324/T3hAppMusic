package com.dunglv.appmusic.ui.bottonshettfragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.PlayListSongAdapter
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetMenuSongBinding

class BottomSheetDialogMenuSong : BaseBindingBottomSheetDialogFragment<BottonsheetMenuSongBinding>() {
    var music: Music? = null
    private var uri: String = ""
    lateinit var mainVM: MainVM

    private val playListSongAdapter: PlayListSongAdapter by lazy {
        PlayListSongAdapter().apply {
            iClickSongPlay = { _, item ->
                addItem1(uri,item.id)
            }
        }
    }
    private val bottomSheetAddPlayList: BottomSheetAddPlayList by lazy {
        BottomSheetAddPlayList().apply {
            iClickAdd = { addItem(it) }
        }
    }

    override val layoutId = R.layout.bottonsheet_menu_song

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        mainVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        initAdapter()
        onClick()
        initData()
    }

    private fun initAdapter() {
        binding.rcSong.adapter = playListSongAdapter
    }

    private fun initData() {
        context?.let {
            getTokenFromSharedPreferences(it)?.let { path ->
                uri = path
            }
        }
        binding.tvName.text = music?.nameSong
        with(mainVM) {
            if (listPlayListLiveData.value.isNullOrEmpty()) {
                getAllPlayList()
            }
            listPlayListLiveData.observeWithCatch(viewLifecycleOwner) { data ->
                playListSongAdapter.submitList(data)
            }
        }
    }

    private fun addItem(name: String) {
        mainVM.insertItemPlayList(name)
    }

    private fun addItem1(uri: String,id:Long) {
        uri.let { mainVM.insertMusicPlayList(it, id) }
        toast(getString(R.string.th_m_v_o_danh_s_ch1))
    }

    private fun onClick() {
        binding.tvPlayListNew.setOnSafeClick {
            bottomSheetAddPlayList.show(
                childFragmentManager.beginTransaction().remove(bottomSheetAddPlayList),
                bottomSheetAddPlayList.tag
            )
        }
    }

    private fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.KEY_URI_PLAY_LIST, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constant.KEY_URI, "0")
    }
}