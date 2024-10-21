package com.dunglv.appmusic.ui.playlist.add.typeadd

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.TypeRecentAddAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentTypeRecentBinding

class TypeRecentAddFragment : BaseBindingFragment<FragmentTypeRecentBinding, MainVM>() {

    private val typeRecentAddAdapter: TypeRecentAddAdapter by lazy {
        TypeRecentAddAdapter().apply {
            iClickMenu = { _, recently -> addItem(recently.uri) }
        }
    }

    override fun getViewModel(): Class<MainVM> {
        return MainVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_type_recent

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        binding.rcMusic.adapter = typeRecentAddAdapter

    }

    private fun initData() {
//
        mainVM.listRecentLiveData.let {
            if (it.value.isNullOrEmpty()) {
                mainVM.getAllRecently()
            }
            it.observeWithCatch(viewLifecycleOwner) { data ->
                data.checkSizeMusic()
                typeRecentAddAdapter.submitList(data)
            }
        }
    }

    private fun addItem(uri: String?) {
        uri?.let { mainVM.insertRecentAdd(it, mainVM.currentPlayList) }
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