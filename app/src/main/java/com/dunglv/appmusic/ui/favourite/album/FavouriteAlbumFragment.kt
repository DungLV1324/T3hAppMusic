package com.dunglv.appmusic.ui.favourite.album

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.ui.adapter.FavouriteAlbumAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.invisible
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentFavouriteAlbumBinding

class FavouriteAlbumFragment :
    BaseBindingFragment<FragmentFavouriteAlbumBinding, FavouriteAlbumViewModel>() {

    private val favouriteAlbumAdapter: FavouriteAlbumAdapter by lazy {
        FavouriteAlbumAdapter().apply {
            iClickSongPlay = { _, music -> navigateAlbum(music.album) }
        }
    }

    override fun getViewModel() = FavouriteAlbumViewModel::class.java

    override val layoutId = R.layout.fragment_favourite_album

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
        with(mainVM) {
            listHeartLiveData.let { data ->
                if (data.value.isNullOrEmpty()) {
                    getAllHeart()
                }
                data.observeWithCatch(viewLifecycleOwner) { list ->
                    list.checkSizeMusic()
                    favouriteAlbumAdapter.submitList(list.distinctBy { it.album })
                }
            }
        }
    }

    private fun initAdapter() {
        binding.rcFavouriteAlbum.adapter = favouriteAlbumAdapter
    }

    private fun navigateAlbum(album: String?) {
        runCatching {
            Bundle().apply {
                putString(Constant.KEY_NAME, album)
                navigateBundle(R.id.fragment_album_belong_to_song, this)
            }
        }.onFailure { it.printStackTrace() }
    }
}