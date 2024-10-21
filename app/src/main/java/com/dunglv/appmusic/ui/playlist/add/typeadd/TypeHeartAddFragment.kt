package com.dunglv.appmusic.ui.playlist.add.typeadd

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.adapter.TypeHeartAddAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.ui.playlist.PlayListViewModel
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentTypeHeartBinding

class TypeHeartAddFragment : BaseBindingFragment<FragmentTypeHeartBinding, PlayListViewModel>() {

    private val typeAddAdapter: TypeHeartAddAdapter by lazy {
        TypeHeartAddAdapter().apply {
            iClickAdd = { _, music -> addItem(music.uri) }
        }
    }

    override val layoutId: Int get() = R.layout.fragment_type_heart

    override fun getViewModel(): Class<PlayListViewModel> = PlayListViewModel::class.java

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        binding.rcMusic.adapter = typeAddAdapter
    }
    private fun initData() {
        mainVM.getAllHeart()
        mainVM.listHeartLiveData.observeWithCatch(viewLifecycleOwner) {
            it.let { data ->
                it.checkSizeMusic()
                typeAddAdapter.submitList(data)
            }
        }
    }

    private fun addItem(uri: String?) {
        uri?.let { mainVM.insertHeartAdd(it, mainVM.currentPlayList) }
        toast(getString(R.string.th_m_v_o_danh_s_ch1))
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.imCheckData.visible()
        } else {
            binding.imCheckData.invisible()
        }
    }
}
