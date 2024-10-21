package com.dunglv.appmusic.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dunglv.appmusic.ui.home.album.AlbumFragment
import com.dunglv.appmusic.ui.home.folder.FolderFragment
import com.dunglv.appmusic.ui.home.singer.SingerFragment
import com.dunglv.appmusic.ui.home.song.SongFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val songFragment: SongFragment by lazy { SongFragment() }
    private val singerFragment: SingerFragment by lazy { SingerFragment() }
    private val albumFragment: AlbumFragment by lazy { AlbumFragment() }
    private val folderFragment: FolderFragment by lazy { FolderFragment() }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> singerFragment
            2 -> albumFragment
            3 -> folderFragment
            else -> songFragment
        }
    }

    override fun getItemCount() = 4
}