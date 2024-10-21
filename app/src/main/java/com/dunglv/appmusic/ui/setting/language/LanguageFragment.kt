package com.dunglv.appmusic.ui.setting.language

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.adapter.LanguageAdapter
import com.dunglv.appmusic.ui.base.BaseBindingFragment
import com.dunglv.appmusic.ui.base.observeWithCatch
import com.dunglv.appmusic.utils.LanguageUtils.changeLang
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentLanguaeBinding

class LanguageFragment : BaseBindingFragment<FragmentLanguaeBinding, LanguageViewModel>() {

    private var currentLanguage = "en"
    private var oldLanguage = "en"
    override fun getViewModel() = LanguageViewModel::class.java

    override val layoutId = R.layout.fragment_languae

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        onClick()
        initAdapter()
        initData()
    }

    private val languageAdapter: LanguageAdapter by lazy {
        LanguageAdapter().apply {
            iClickItem = { language, pos ->
                language.code?.let { viewModel.updateSelectOneView(pos) }
                currentLanguage = language.code ?: currentLanguage
            }
        }
    }

    private fun initAdapter() {
        binding.rcLanguage.adapter = languageAdapter
    }

    private fun initData() {
        currentLanguage = oldLanguage
        viewModel.getAllLanguage()
        viewModel.listLanguageLiveData.observeWithCatch(viewLifecycleOwner) {
            languageAdapter.submitList(it)
        }
    }

    private fun onClick() {
        binding.imBack.setOnSafeClick {
            if (activity is MainActivity) {
                (activity as MainActivity).navController.popBackStack()
            }
        }
        binding.tvDone.setOnSafeClick {
            Intent().apply {
                context?.changeLang(currentLanguage)
                activity?.finish()
                startActivity(activity?.intent)
            }
        }
    }
}