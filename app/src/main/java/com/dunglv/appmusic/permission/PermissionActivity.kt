package com.dunglv.appmusic.permission

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusic.ui.MainVM
import com.dunglv.appmusic.ui.base.BaseBindingActivity
import com.dunglv.appmusic.ui.dialog.PermissionsDialog
import com.dunglv.appmusic.utils.extention.gone
import com.dunglv.appmusic.utils.extention.isAtLeastSdkVersion
import com.dunglv.appmusic.utils.extention.isGrantNotificationPermission
import com.dunglv.appmusic.utils.extention.isGrantPermission
import com.dunglv.appmusic.utils.extention.setOnSafeClick
import com.dunglv.appmusic.utils.extention.setStatusBarColor
import com.dunglv.appmusic.utils.extention.visible
import com.dunglv.appmusic.utils.setLightStatusBars
import com.dunglv.appmusickl.R
import com.dunglv.appmusickl.databinding.FragmentPermissionBinding

class PermissionActivity : BaseBindingActivity<FragmentPermissionBinding, MainVM>() {
    private var isGrantNotification: Boolean = false
        set(value) {
            field = value
            binding.imNotificationPermission.showSwitchOnOff(isGrantNotification)
            showTvGo()
        }

    private var isGrantAudio: Boolean = false
        set(value) {
            field = value
            binding.imAudioPermission.showSwitchOnOff(isGrantAudio)
            showTvGo()
        }

    private val requestPermissionAudio =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isGrantAudio = true
            } else {
                isGrantAudio = false
                PermissionsDialog().show(supportFragmentManager, "")
            }
        }

    private val requestPermissionNotification =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isGrantNotification = true
            } else {
                isGrantNotification = false
                PermissionsDialog().show(supportFragmentManager, "")
            }
        }

    override val layoutId = R.layout.fragment_permission

    override fun getViewModel() = MainVM::class.java

    override fun setupView(savedInstanceState: Bundle?) {
        initPermission()
        initAction()
        window.setLightStatusBars(false)
        setStatusBarColor("#161D29")
    }

    override fun setupData() {
    }

    override fun onResume() {
        super.onResume()
        initPermission()
    }

    private fun initPermission() {
        isGrantAudio = if (isAtLeastSdkVersion(33)) {
            viewModel.checkPermission = isGrantAudio
            isGrantPermission(READ_MEDIA_AUDIO)
        } else {
            isGrantPermission(READ_EXTERNAL_STORAGE)
        }
        isGrantNotification = isGrantNotificationPermission()
    }

    private fun initAction() {
        binding.tvGo.setOnSafeClick {
            startMainAct()
        }

        binding.imAudioPermission.setOnSafeClick {
            if (isAtLeastSdkVersion(33)) {
                if (!isGrantPermission(READ_MEDIA_AUDIO)) {
                    requestPermissionAudio.launch(READ_MEDIA_AUDIO)
                }
            } else {
                if (!isGrantPermission(READ_EXTERNAL_STORAGE)) {
                    requestPermissionAudio.launch(READ_EXTERNAL_STORAGE)
                }
            }
        }

        binding.imNotificationPermission.setOnSafeClick {
            if (!isGrantNotificationPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun startMainAct() = Intent(this, MainActivity::class.java).apply {
        startActivity(this)
        finish()
    }

    private fun showTvGo() = if (isGrantAudio && isGrantNotification) {
        binding.tvGo.visible()
    } else {
        binding.tvGo.gone()
    }

    private fun ImageView.showSwitchOnOff(isOn: Boolean) = if (isOn) {
        setImageResource(R.drawable.ic_on)
    } else {
        setImageResource(R.drawable.ic_off)
    }
}



