package com.dunglv.appmusic.ui.bottonshettfragment

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetArrangeAlbumBinding

class BottomSheetAlbumArrangeDialog :
    BaseBindingBottomSheetDialogFragment<BottonsheetArrangeAlbumBinding>() {
    var iClickAlbum: () -> Unit = {}
    var iClickAlbumZA: () -> Unit = {}
    var iClickSinger: () -> Unit = {}

    override val layoutId = R.layout.bottonsheet_arrange_album

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
    }

    private fun onClick() {
        binding.tvInName.setOnSafeClick {
            iClickAlbum()
            dismiss()
        }

        binding.tvInNameZa.setOnSafeClick {
            iClickAlbumZA()
            dismiss()
        }

        binding.tvSing.setOnSafeClick {
            iClickSinger()
            dismiss()
        }
    }
}