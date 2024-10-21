package com.dunglv.appmusic.ui.home.album

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Album
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.AlbumAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.bottonshettfragment.BottomSheetAlbumArrangeDialog
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentAlbumBinding

class AlbumFragment : BaseBindingFragment<FragmentAlbumBinding, MainVM>() {

    private val albumAdapter: AlbumAdapter by lazy {
        AlbumAdapter().apply {
            iClickNext = { _, it -> initSwitchScreenAllAlbum(it) }
            iClickSongPlay = { _, it -> initSwitchScreenAllAlbum(it) }
        }
    }

    override fun getViewModel() = MainVM::class.java

    override val layoutId = R.layout.fragment_album

    private val bottomSheetArrangeDialog: BottomSheetAlbumArrangeDialog by lazy {
        BottomSheetAlbumArrangeDialog().apply {
            iClickAlbum = { mainVM.sortAlbum() }
            iClickAlbumZA = { mainVM.sortAlbumZA() }
            iClickSinger= { mainVM.sortSingAlbum() }
        }
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        setInOnClick()
    }

    private fun setInOnClick() {
        binding.imArrange.setOnSafeClick {
            showDialogArrange()
        }

        binding.imPlay.setOnSafeClick {
            mainVM.listMusicLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun showDialogArrange() = bottomSheetArrangeDialog.show(childFragmentManager, tag)

    private fun initData() {
        mainVM.listAlbumLiveData.observeWithCatch(viewLifecycleOwner) {
            it?.let { data -> albumAdapter.submitList(data) }
        }
    }

    private fun initAdapter() {
        binding.rcAlbum.adapter = albumAdapter
    }

    private fun initSwitchScreenAllAlbum(album: Album) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_NAME, album.name)
                navigateBundle(R.id.fragment_album_belong_to_song, this)
            }
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
}
