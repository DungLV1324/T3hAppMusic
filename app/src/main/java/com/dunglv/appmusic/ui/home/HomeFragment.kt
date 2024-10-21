package com.dunglv.appmusic.ui.home

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import com.dunglv.appmusic.ui.adapter.pager.ViewPagerAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.gone
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.startSetting
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var iClickGoTo = false

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override val layoutId = R.layout.fragment_home

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
        observerData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (iClickGoTo) {
            mainVM.listMusicLiveData.let {
                if (it.value.isNullOrEmpty()) {
                    mainVM.initData()
                }
            }
            iClickGoTo = false
        }
    }

    private fun initViewPager() {
        ViewPagerAdapter(childFragmentManager, lifecycle).apply {
            binding.viewPager2.adapter = this
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                tab.text = getString(
                    when (position) {
                        1 -> (R.string.singer)
                        2 -> (R.string.album)
                        3 -> (R.string.folder)
                        else -> (R.string.song)
                    }
                )
            }.attach()
        }
    }

    private fun onClick() {
        binding.tvGoToSetting.setOnSafeClick {
            activity?.startSetting()
            iClickGoTo = true
        }

        binding.vFavorite.setOnSafeClick {
            navigateFragment(R.id.fragment_favourite)
        }

        binding.vListPlay.setOnSafeClick {
            navigateFragment(R.id.fragment_playlist)
        }

        binding.vClock.setOnSafeClick {
            navigateFragment(R.id.fragment_recently)
        }

        binding.ivMenu.setOnSafeClick {
            navigateFragment(R.id.fragment_setting)
        }

        binding.vSearch.setOnSafeClick {
            navigateFragment(R.id.fragment_search)
        }

        binding.tvSearch.isSelected = true
    }

    private fun observerData() {
        initViewPager()
        mainVM.listMusicLiveData.observeWithCatch(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imEmpty.visible()
                binding.tvGoToSetting.visible()
                binding.tvNote.visible()
                binding.viewPager2.gone()
                binding.tabLayout.gone()
            } else {
                binding.imEmpty.gone()
                binding.tvNote.gone()
                binding.tvGoToSetting.gone()
                binding.viewPager2.visible()
                binding.tabLayout.visible()
            }
        }
    }
}