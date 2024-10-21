package com.dunglv.appmusic.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dunglv.appmusic.ui.favourite.album.FavouriteAlbumFragment
import com.dunglv.appmusic.ui.favourite.sing.FavouriteSingFragment
import com.dunglv.appmusic.ui.favourite.song.FavouriteSongFragment

class ViewPagerFavouriteAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val favouriteSongFragment: FavouriteSongFragment by lazy { FavouriteSongFragment() }
    private val favouriteSingerFragment: FavouriteSingFragment by lazy { FavouriteSingFragment() }
    private val favouriteAlbumFragment: FavouriteAlbumFragment by lazy { FavouriteAlbumFragment() }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            1-> favouriteSingerFragment
            2->favouriteAlbumFragment
           else->favouriteSongFragment
        }
    }

    override fun getItemCount() = 3
}