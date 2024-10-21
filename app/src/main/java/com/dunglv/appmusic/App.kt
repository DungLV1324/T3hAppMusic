package com.dunglv.appmusic

import android.app.Application
import android.content.res.Configuration
import com.dunglv.appmusic.data.local.SharedPreferenceHelper
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.utils.LanguageUtils.setLocale
import com.dunglv.appmusic.utils.MyDebugTree
import com.dunglv.appmusickl.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    var listAllMusic: MutableList<Music> = mutableListOf()

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        this.setLocale()
    }
}