package com.dunglv.appmusic.ui.playlist.add.typeadd

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.TypeDeviceAddAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentTypeAddBinding

class TypeDeviceAddFragment : BaseBindingFragment<FragmentTypeAddBinding, MainVM>() {

    private val typeDeviceAddAdapter: TypeDeviceAddAdapter by lazy {
        TypeDeviceAddAdapter().apply {
            iClickAdd = { _, music -> addItem(music.uri) }
        }
    }

    override fun getViewModel(): Class<MainVM> {
        return MainVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_type_add

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        binding.rcMusic.adapter = typeDeviceAddAdapter
    }

    private fun initData() {
        mainVM.listMusicLiveData.observeWithCatch(viewLifecycleOwner) {
            it.let { data -> typeDeviceAddAdapter.submitList(data) }
        }
    }

    private fun addItem(uri: String?) {
        uri?.let { mainVM.insertMusicAdd(it, mainVM.currentPlayList) }
        toast(getString(R.string.th_m_v_o_danh_s_ch1))
    }
}