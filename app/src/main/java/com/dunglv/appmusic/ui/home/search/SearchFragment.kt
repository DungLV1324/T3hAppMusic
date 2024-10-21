package com.dunglv.appmusic.ui.home.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.SearchAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentSearchBinding

class SearchFragment : BaseBindingFragment<FragmentSearchBinding, SearchViewModel>() {
    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter().apply {
            iClickSongPlay = { _, it -> initSwitchScreen(it) }
        }
    }

    override fun getViewModel() = SearchViewModel::class.java

    override val layoutId = R.layout.fragment_search

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        onClick()
    }

    private fun onClick() {
        binding.vSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.vSearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    viewModel.posNull()
                }else {
                    mainVM.listMusicLiveData.value?.let {
                        viewModel.filterList(newText, it)
                    }
                }
                return true
            }
        })
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun initData() {
        with(viewModel) {
            listSearchLiveData.observeWithCatch(viewLifecycleOwner) {
                it.let { data ->
                    searchAdapter.submitList(data)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rcSearchSong.adapter = searchAdapter
    }

    private fun initSwitchScreen(music: Music) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_URI_MUSIC_CURRENT, music.uri)
                music.uri?.let { mainVM.insertRecent(it) }
                navigateBundle(R.id.fragment_play_music, this)
            }
        }.onFailure { it.printStackTrace() }
    }
}