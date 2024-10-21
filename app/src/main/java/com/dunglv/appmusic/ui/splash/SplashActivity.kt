package com.dunglv.appmusic.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dunglv.appmusic.permission.PermissionActivity
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingActivity
import com.dunglv.appmusic.utils.extention.isGrantAudioPermission
import com.dunglv.appmusic.utils.extention.isGrantNotificationPermission
import com.dunglv.appmusic.utils.extention.setStatusBarColor
import com.dunglv.appmusic.utils.setLightStatusBars
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")

class SplashActivity : BaseBindingActivity<ActivitySplashBinding, MainVM>() {

    override fun setupView(savedInstanceState: Bundle?) {
        window.setLightStatusBars(true)
        setStatusBarColor("#ffffff")
        startToMain()
    }

    override fun setupData() {}

    override val layoutId = R.layout.activity_splash

    override fun getViewModel() = MainVM::class.java

    private fun startToMain() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            if (isGrantAudioPermission() && isGrantNotificationPermission()) {
                startMainAct()
            } else {
                startPermissionAct()
            }
        }
    }

    private fun startMainAct() {
        runCatching {
            Intent(this@SplashActivity, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun startPermissionAct() {
        runCatching {
            Intent(this@SplashActivity, PermissionActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}