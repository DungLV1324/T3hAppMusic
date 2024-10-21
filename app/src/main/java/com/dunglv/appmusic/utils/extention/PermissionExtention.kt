package com.dunglv.appmusic.utils.extention

import android.Manifest
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.AppOpsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.core.content.ContextCompat

fun Context.isGrantPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        this, permission
    ) == PackageManager.PERMISSION_GRANTED
fun Context.isGrantAudioPermission() =
    if (isAtLeastSdkVersion(33)) {
        isGrantPermission(Manifest.permission.READ_MEDIA_AUDIO)
    } else {
        isGrantPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
fun Context.isGrantNotificationPermission() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        isGrantPermission(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        true
    }
