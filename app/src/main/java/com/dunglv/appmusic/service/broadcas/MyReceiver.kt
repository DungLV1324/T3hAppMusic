package com.dunglv.appmusic.service.broadcas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.service.MusicService

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val actionMusic = intent.getIntExtra(Constant.KEY_ACTION_CLICK_NOTI, -1)
        when (actionMusic) {
            Constant.BRO_PLAY_MUSIC ,Constant.BRO_PAUSE_MUSIC-> {
                Intent(context, MusicService::class.java).apply {
                    action = Constant.KEY_PLAY_MUSIC
                    context?.startService(this)
                }
            }

            Constant.BRO_COMEBACK_MUSIC -> {
                Intent(context, MusicService::class.java).apply {
                    action = Constant.KEY_COMEBACK_MUSIC
                    context?.startService(this)
                }
            }

            Constant.BRO_NEXT_MUSIC -> {
                Intent(context, MusicService::class.java).apply {
                    action = Constant.KEY_NEXT_MUSIC
                    context?.startService(this)
                }
            }
            else -> {}
        }
    }
}