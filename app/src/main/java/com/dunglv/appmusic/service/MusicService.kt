package com.dunglv.appmusic.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.dunglv.appmusic.App
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.common.Constant.KEY_COMEBACK_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_NEXT_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_PLAY_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_SEEK_TO_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_START_MUSIC
import com.dunglv.appmusic.common.Constant.KEY_URI_MUSIC
import com.dunglv.appmusic.common.Constant.SEEK_TO_MUSIC
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.utils.MediaUT
import com.dunglv.appmusic.utils.NotifiUT1
import java.util.Random

class MusicService : Service() {
    private var mediaUT: MediaUT? = null
    private var notificationUtils: NotifiUT1? = null
    var uri = ""

    override fun onCreate() {
        super.onCreate()
        notificationUtils = NotifiUT1(this)
        startForeground(1, notificationUtils?.getNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, notificationUtils?.getNotification())
        if (mediaUT == null) mediaUT = MediaUT(this).apply {
            onComplete = {
                when (getTokenFromSharedPreferences(context)) {
                    Constant.KEY_ALL_LOOP -> nextMusic()
                    Constant.KEY_ONLY_LOOP -> initMediaPlayer(uri, "", "")
                    Constant.KEY_RANDOM_LOOP -> ranDomMusic()
                }
            }
        }

        intent?.let { data ->
            when (data.action) {
                KEY_START_MUSIC -> {
                    data.getStringExtra(KEY_URI_MUSIC)?.let { path ->
                        uri = path
                        notificationUtils?.setActionPause()

                        App.instance.listAllMusic.let { allMusic ->
                            allMusic.firstOrNull { path == it.uri }?.let {
                                notificationUtils?.setName(it.nameSong, it.nameSing)
                                mediaUT?.initMediaPlayer(uri, it.nameSong, it.nameSing)
                            }
                        }
                    }
                }

                KEY_SEEK_TO_MUSIC -> {
                    val progress = intent.getIntExtra(SEEK_TO_MUSIC, 0)
                    mediaUT?.seekToMusic(progress)
                }

                KEY_PLAY_MUSIC -> {
                    mediaUT?.let {
                        it.playMusic()
                        if (it.isPlaying()) {
                            notificationUtils?.setActionPause()
                        } else {
                            notificationUtils?.setActionPlay()
                        }
                    }
                }

                KEY_NEXT_MUSIC -> {
                    nextMusic()
                    notificationUtils?.setActionNext()

                }

                KEY_COMEBACK_MUSIC -> {
                    previousMusic()
                    notificationUtils?.setActionNext()
                }

                else -> {

                }
            }
        }

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun getNextPosMusic(): Int {
        App.instance.listAllMusic.let { allMusic ->
            var currentPos = allMusic.indexOfFirst { it.uri == uri }
            if (currentPos == allMusic.size - 1) {
                currentPos = 0
            } else {
                currentPos += 1
            }
            return currentPos
        }
    }

    private fun nextMusic() = App.instance.listAllMusic.let { allMusic ->
        val posNext = getNextPosMusic()
        uri = allMusic[posNext].uri ?: ""
        allMusic[posNext].startMusic()
    }

    private fun Music.startMusic() {
        mediaUT?.initMediaPlayer(uri ?: "", nameSong, nameSing)
        notificationUtils?.setName(nameSong, nameSing)
    }

    private fun previousMusic() = App.instance.listAllMusic.let { allMusic ->
        val posPrevious = getPreviousMusic()
        uri = allMusic[posPrevious].uri ?: ""
        allMusic[posPrevious].startMusic()
    }

    private fun getPreviousMusic(): Int {
        App.instance.listAllMusic.let { allMusic ->
            var currentPos = allMusic.indexOfFirst { it.uri == uri }
            if (currentPos == 0) {
                currentPos = allMusic.size - 1
            } else {
                currentPos -= 1
            }
            return currentPos
        }
    }

    private fun ranDomMusic() = App.instance.listAllMusic.let { allMusic ->
        val posPrevious = getRanDomMusic()
        uri = allMusic[posPrevious].uri ?: ""
        allMusic[posPrevious].startMusic()
    }

    private fun getRanDomMusic(): Int {
        App.instance.listAllMusic.let { allMusic ->
            val random = Random()
            val index = random.nextInt(allMusic.size)
            var currentPos = allMusic.indexOfFirst { it.uri == uri }
            if (index == currentPos) {
                currentPos = index - 1
            } else {
                currentPos = index
            }
            return currentPos
        }
    }

    private fun getTokenFromSharedPreferences(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(Constant.MUSIC, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(Constant.TYPE, 0)
    }
}