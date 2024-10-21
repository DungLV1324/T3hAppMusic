package com.dunglv.appmusic.ui.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dunglv.appmusic.ui.playlist.add.typeadd.TypeDeviceAddFragment
import com.dunglv.appmusic.ui.playlist.add.typeadd.TypeHeartAddFragment
import com.dunglv.appmusic.ui.playlist.add.typeadd.TypeRecentAddFragment

class ViewPagerAddAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val deviceAddFragment: TypeDeviceAddFragment by lazy { TypeDeviceAddFragment() }
    private val heartAddFragment: TypeHeartAddFragment by lazy { TypeHeartAddFragment() }
    private val recentAddFragment: TypeRecentAddFragment by lazy { TypeRecentAddFragment() }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            1-> heartAddFragment
            2->recentAddFragment
           else->deviceAddFragment
        }
    }
    override fun getItemCount() = 3
}