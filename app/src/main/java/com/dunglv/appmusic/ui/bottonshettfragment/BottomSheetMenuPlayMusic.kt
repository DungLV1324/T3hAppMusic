package com.dunglv.appmusic.ui.bottonshettfragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetMenuPlayListBinding

class BottomSheetMenuPlayMusic : BaseBindingBottomSheetDialogFragment<BottonsheetMenuPlayListBinding>() {
    var iClick: (uri: String) -> Unit = {}
    lateinit var mainVM : MainVM

    override val layoutId = R.layout.bottonsheet_menu_play_list

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        mainVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        onClick()
    }

    private fun onClick() {
        binding.tvBiaAlbumHienTai.setOnSafeClick {
            mainVM.musicPlayLiveData.value?.uri?.let { it1 -> iClick(it1) }
            dismiss()
        }
    }
}