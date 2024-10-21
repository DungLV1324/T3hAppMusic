package com.dunglv.appmusic.ui.bottonshettfragment

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetArrangeBinding

class BottomSheetArrangeDialog : BaseBindingBottomSheetDialogFragment<BottonsheetArrangeBinding>() {
    var iClickAZ: () -> Unit = {}
    var iClickZA: () -> Unit = {}
    var iClickNameSing: () -> Unit = {}
    var iClickTime: () -> Unit = {}

    override val layoutId = R.layout.bottonsheet_arrange

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
    }

    private fun onClick() {
        binding.tvInName.setOnSafeClick {
            iClickAZ()
            dismiss()
        }

        binding.tvInNameZA.setOnSafeClick {
            iClickZA()
            dismiss()
        }

        binding.tvNameSing.setOnSafeClick {
            iClickNameSing()
            dismiss()
        }

        binding.tvTime.setOnSafeClick {
            iClickTime()
            dismiss()
        }
    }
}