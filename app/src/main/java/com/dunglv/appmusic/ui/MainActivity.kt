package com.dunglv.appmusic.ui

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dunglv.appmusic.ui.base.BaseBindingActivity
import com.dunglv.appmusic.utils.makeStatusBarLight
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainVM>() {
    private val navHostFragment: NavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }
    val navController: NavController by lazy { navHostFragment.navController }
    private var isDispatchTouchEvent = true

    override val layoutId = R.layout.activity_main

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return !isDispatchTouchEvent || super.dispatchTouchEvent(ev)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        makeStatusBarLight(Color.parseColor("#00000000"))
    }

    override fun setupData() {
        viewModel.initData()
        viewModel.initAlbum()
    }

    override fun getViewModel(): Class<MainVM> {
        return MainVM::class.java
    }
}