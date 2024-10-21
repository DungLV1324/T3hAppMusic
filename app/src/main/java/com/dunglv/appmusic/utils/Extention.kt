package com.dunglv.appmusic.utils

import android.app.Activity
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Int.getTimeFromDuration(): String {
    val hour = this / (60 * 60 * 1000)
    val minute = (this - hour * (60 * 60 * 1000)) / (60 * 1000)
    val second = (this - hour * (60 * 60 * 1000) - minute * (60 * 1000)) / (1000)
    return "${minute.toString().padStart(2, '0')
            }:${second.toString().padStart(2, '0')}"
}

fun Activity.makeStatusBarLight(color: Int) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val controller = WindowInsetsControllerCompat(window, window.decorView)
    controller.isAppearanceLightStatusBars = true
    window.statusBarColor = color
}

fun Window.setLightStatusBars(b: Boolean) {
    WindowCompat.getInsetsController(this, decorView).isAppearanceLightStatusBars = b
}
