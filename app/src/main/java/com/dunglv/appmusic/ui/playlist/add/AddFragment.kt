package com.dunglv.appmusic.ui.playlist.add

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.adapter.pager.ViewPagerAddAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayoutMediator

class AddFragment : BaseBindingFragment<FragmentAddBinding, MainVM>() {

    override fun getViewModel(): Class<MainVM> {
        return MainVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_add

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
        ViewPagerAddAdapter(childFragmentManager, lifecycle).apply {
            binding.viewPager2.adapter = this
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                with(tab) {
                    when (position) {
                        1 -> setIcon(R.drawable.ic_heart_add)
                        2 -> setIcon(R.drawable.ic_clock_add)
                        else -> setIcon(R.drawable.ic_phone_add)
                    }
                }
            }.attach()

        }
    }
}