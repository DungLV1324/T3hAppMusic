package com.dunglv.appmusic.ui.dialog

import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.base.BaseBindingDialogFragment
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.DialogRateBinding

class RateDialog : BaseBindingDialogFragment<DialogRateBinding>() {
    override val layoutId = R.layout.dialog_rate
    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
        initData()
    }

    private fun initData() {
        binding.rtRate.setOnRatingBarChangeListener { ratingBar, f1, b ->
            binding.tvName.text = f1.toString()
            binding.tvTitle.text = b.toString()
            when (ratingBar.rating.toInt()) {
                0 -> {
                    binding.tvName.text = getString(R.string.name0)
                    binding.imAvatar.setImageResource(  R.drawable.ic_start_0)
                    binding.tvTitle.text = getString(R.string.title0)
                }

                1 -> {
                    binding.tvName.text = getString(R.string.name1)
                    binding.tvTitle.text = getString(R.string.title321)
                    binding.imAvatar.setImageResource(  R.drawable.ic_start1)
                }

                2 -> {
                    binding.tvName.text = getString(R.string.name2)
                    binding.imAvatar.setImageResource(  R.drawable.ic_start3)
                    binding.tvTitle.text = getString(R.string.title321)
                }

                3 -> {
                    binding.tvName.text = getString(R.string.name3)
                    binding.imAvatar.setImageResource(  R.drawable.ic_star4)
                    binding.tvTitle.text = getString(R.string.title321)
                }

                4 -> {
                    binding.tvName.text = getString(R.string.name4)
                    binding.imAvatar.setImageResource(  R.drawable.ic_start3)
                    binding.tvTitle.text = getString(R.string.title54)
                }

                5 -> {
                    binding.tvName.text = getString(R.string.name5)
                    binding.imAvatar.setImageResource(  R.drawable.ic_star5)
                    binding.tvTitle.text = getString(R.string.title54)
                }
            }
        }
    }

    private fun onClick() {
        binding.tvRate.setOnSafeClick {
            dismiss()
        }
        binding.tvExit.setOnSafeClick {
            dismiss()
        }
    }
}