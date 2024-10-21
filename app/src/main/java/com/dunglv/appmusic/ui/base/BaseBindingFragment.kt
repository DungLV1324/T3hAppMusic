package com.dunglv.appmusic.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseBindingFragment<B : ViewDataBinding, T : BaseViewModel> : BaseFragment() {
    lateinit var binding: B
    lateinit var viewModel: T
    lateinit var mainVM: MainVM
    private var lastClickTime: Long = 0

    protected abstract fun getViewModel(): Class<T>
    abstract val layoutId: Int
    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    fun toast(text: String) {
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1500)
            toast?.cancel()
        }
    }

    protected abstract fun onCreatedView(view: View?, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        viewModel = ViewModelProvider(this)[getViewModel()]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreatedView(view, savedInstanceState)
    }

    private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    fun initSheet(view: View): BottomSheetBehavior<*> {
        return BottomSheetBehavior.from<View>(view)
    }

    open fun navigateBundle(id: Int, bundle: Bundle?) {
        if (activity is MainActivity) {
            (activity as MainActivity).navController.navigate(id, bundle)
        }
    }

    open fun navigateFragment(id: Int) {
        (activity as MainActivity).navController.navigate(id)
    }
}