package com.dunglv.appmusic.data.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(sharedPreferences: SharedPreferences) {
    companion object {
        private const val DEFAULT_NUM = -1
        lateinit var sharedPreferences: SharedPreferences
        fun storeString(key: String?, value: String?) {
            sharedPreferences.edit().putString(key, value).apply()
        }

        fun getStringWithDefault(key: String?, defaultValue: String): String {
            return sharedPreferences.getString(key, defaultValue) ?: defaultValue
        }

        fun getLong(key: String?): Long {
            return sharedPreferences.getLong(key, DEFAULT_NUM.toLong())
        }
    }

    init {
        Companion.sharedPreferences = sharedPreferences
    }
}