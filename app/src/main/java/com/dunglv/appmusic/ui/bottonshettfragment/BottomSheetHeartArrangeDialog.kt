package com.dunglv.appmusic.ui.bottonshettfragment

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetArrangeBinding

class BottomSheetHeartArrangeDialog : BaseBindingBottomSheetDialogFragment<BottonsheetArrangeBinding>() {
    var iClickFavourSong: () -> Unit = {}
    var iClickFavourSongZA: () -> Unit = {}
    var iClickFavourSing: () -> Unit = {}
    var iClickFavourTime: () -> Unit = {}

    override val layoutId = R.layout.bottonsheet_arrange

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
    }

    private fun onClick() {
        binding.tvInName.setOnSafeClick {
          iClickFavourSong()
            dismiss()
        }
        binding.tvInNameZA.setOnSafeClick {
            iClickFavourSongZA()
            dismiss()
        }
        binding.tvNameSing.setOnSafeClick {
            iClickFavourSing()
            dismiss()
        }
        binding.tvTime.setOnSafeClick {
            iClickFavourTime()
            dismiss()
        }
    }
}