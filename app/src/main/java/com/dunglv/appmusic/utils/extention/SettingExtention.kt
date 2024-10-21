package com.dunglv.appmusic.utils.extention

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager


fun Context.shareMusic(uri: String?) = runCatching {
    Intent().apply {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "audio/*"
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri))
        startActivity(Intent.createChooser(share, "Share Sound File"))
    }
}.onFailure { it.printStackTrace() }

fun Activity.startSetting() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
    data = Uri.fromParts("package", packageName, null)
    startActivity(this)
}


fun Activity.setStatusBarColor(color: String) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.parseColor(color)
    window.navigationBarColor = Color.parseColor(color)
}

fun isAtLeastSdkVersion(versionCode: Int): Boolean {
    return Build.VERSION.SDK_INT >= versionCode
}