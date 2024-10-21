package com.dunglv.appmusic.ui.favourite

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.pager.ViewPagerFavouriteAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentFavouriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavouriteFragment : BaseBindingFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    override fun getViewModel()= FavouriteViewModel::class.java

    override val layoutId = R.layout.fragment_favourite

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        onClick()
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun initData() {
        ViewPagerFavouriteAdapter(childFragmentManager, lifecycle).apply {
            binding.viewPager2.adapter = this
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                with(tab) {
                    when (position) {
                        1 -> setIcon(R.drawable.ic_microphone)
                        2 -> setIcon(R.drawable.ic_albumm)
                        else -> setIcon(R.drawable.ic_music_sing)
                    }
                }
            }.attach()
        }
    }
}