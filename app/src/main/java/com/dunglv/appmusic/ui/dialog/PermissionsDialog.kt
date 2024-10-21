package com.dunglv.appmusic.ui.dialog

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.base.BaseBindingDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.startSetting
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.DialogPermissonBinding


class PermissionsDialog : BaseBindingDialogFragment<DialogPermissonBinding>() {

    override val layoutId = R.layout.dialog_permisson

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
    }

    private fun onClick() {
        binding.tvNo.setOnSafeClick {
            dismiss()
        }

        binding.tvYes.setOnSafeClick {
            activity?.startSetting()
            dismiss()
        }
    }
}