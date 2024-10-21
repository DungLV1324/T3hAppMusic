package com.dunglv.appmusic.utils.extention

import android.content.Context
import android.content.Intent
import android.os.Build

fun Intent.startService(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(this)
    } else {
        context.startService(this)
    }
}