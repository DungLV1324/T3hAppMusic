package com.dunglv.appmusic.ui.favourite.sing

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.adapter.FavouriteSingAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentFavouriteSingBinding

class FavouriteSingFragment : BaseBindingFragment<FragmentFavouriteSingBinding, FavouriteSingViewModel>() {

    private val favouriteSingAdapter: FavouriteSingAdapter by lazy {
        FavouriteSingAdapter().apply {
            iClickSongPlay = { _, music -> navigateSing(music.nameSing) }
            iClickMenu = { _, music -> navigateSing(music.nameSing) }
        }
    }

    override fun getViewModel() = FavouriteSingViewModel::class.java

    override val layoutId = R.layout.fragment_favourite_sing

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun List<Music>.checkSizeMusic() {
        if (this.isEmpty()) {
            binding.tvCheckData.visible()
        } else {
            binding.tvCheckData.invisible()
        }
    }

    private fun initData() {
        mainVM.listHeartLiveData.observeWithCatch(viewLifecycleOwner) { sing->
            sing.let {data->
                sing.checkSizeMusic()
                favouriteSingAdapter.submitList(data.distinctBy { it.nameSing })
            }
        }
    }

    private fun initAdapter() {
        binding.rcFavouriteSing.adapter=favouriteSingAdapter
    }

    private fun navigateSing (nameSing: String?) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_NAME, nameSing)
                navigateBundle(R.id.fragment_sing_belong_to_song, this)
            }
        }.onFailure { it.printStackTrace() }
    }
}