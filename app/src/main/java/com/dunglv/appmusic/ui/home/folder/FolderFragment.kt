package com.dunglv.appmusic.ui.home.folder

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Folder
import com.dunglv.appmusic.ui.adapter.FolderAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentForderBinding

class FolderFragment : BaseBindingFragment<FragmentForderBinding, FolderViewModel>() {

    private val folderAdapter: FolderAdapter by lazy {
        FolderAdapter().apply {
            iClickSongPlay = { _, it -> initSwitchScreenAllFolder(it) }
        }
    }

    override fun getViewModel() = FolderViewModel::class.java

    override val layoutId = R.layout.fragment_forder

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun onClick() {
        binding.imPlay.setOnSafeClick {
            mainVM.listMusicLiveData.value?.let { all ->
                navigatePlayMusic(all.random().uri)
            }
        }
    }

    private fun initData() {
        with(viewModel) {
            initFolder()
            listFolderLiveData.observeWithCatch(viewLifecycleOwner) {
                it?.let { data ->
                    folderAdapter.submitList(data)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rcFolder.adapter = folderAdapter
    }

    private fun initSwitchScreenAllFolder(folder: Folder) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_NAME, folder.nameFolder)
                navigateBundle(R.id.fragment_folder_belong_to_song, this)
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