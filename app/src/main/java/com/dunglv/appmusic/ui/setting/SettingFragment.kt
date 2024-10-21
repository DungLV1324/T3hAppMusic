package com.dunglv.appmusic.ui.setting

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.dialog.RateDialog
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentSettingBinding

class
SettingFragment : BaseBindingFragment<FragmentSettingBinding, MainVM>() {

    override fun getViewModel() = MainVM::class.java

    override val layoutId = R.layout.fragment_setting

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            (activity as MainActivity).navController.popBackStack()
        }

        binding.tvLanguage.setOnSafeClick {
            navigateFragment(R.id.fragment_languae)
        }

        binding.tvRate.setOnSafeClick {
            RateDialog().show(childFragmentManager, tag)
        }
    }
}



