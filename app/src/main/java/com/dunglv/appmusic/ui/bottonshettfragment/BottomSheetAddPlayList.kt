package com.dunglv.appmusic.ui.bottonshettfragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingBottomSheetDialogFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.BottonsheetAddBinding

class BottomSheetAddPlayList : BaseBindingBottomSheetDialogFragment<BottonsheetAddBinding>() {
    var iClickAdd: (String) -> Unit = {}
    lateinit var mainVM: MainVM

    override val layoutId = R.layout.bottonsheet_add

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        mainVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        onClick()
        observerData()
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);

    }

    private fun observerData() {
        mainVM.isExistPlayListLiveData.observeWithCatch(viewLifecycleOwner) { isExist ->
            if (isExist) {
                toast(getString(R.string.namePlayListTrung))
            } else {
                val text = binding.edName.text.toString().trim()
                iClickAdd(text)
                dismiss()
            }
            mainVM.postNullExist()
        }
    }

    private fun onClick() {
        binding.tvNo.setOnSafeClick {
            dismiss()
        }
        binding.tvYes.setOnSafeClick {
                val text = binding.edName.text.toString().trim()
                if (text.isEmpty()) {
                    toast(getString(R.string.namePlayListNUll))
                } else {
                    mainVM.isExistPlayList(text)
                }
            hideSoftKeyboard()
        }
    }

    private fun hideSoftKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}