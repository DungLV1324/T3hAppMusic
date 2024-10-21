package com.dunglv.appmusic.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.common.MessageEventMusic
import org.greenrobot.eventbus.EventBus

class MediaUT(val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    var uri = ""
    var onStart: () -> Unit = {}
    var onComplete: () -> Unit = {}
    var onError: () -> Unit = {}
    var onUpdateText: () -> Unit = {}
    fun seekToMusic(progress: Int) {
        runCatching {
            mediaPlayer?.seekTo(progress)
        }.onFailure { it.printStackTrace() }
    }

    fun initMediaPlayer(url: String, nameSong: String, nameSing: String) {
        stopMusic()
        onUpdateText()
        uri = url
        runCatching {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepare()
                setOnPreparedListener {
                    start()
                    EventBus.getDefault().post(
                        MessageEventMusic(Constant.EVENT_START_MUSIC, url, it.duration)
                    )
                    updateTime()
                    onStart()
                }
                setOnCompletionListener {
                    onComplete()
                }

                setOnErrorListener { mediaPlayer, i, i2 ->
                    onError()
                    true
                }
            }

        }.onFailure { it.printStackTrace() }
    }


    private fun updateTime() {
        val handler = Handler()
        var time = ""
        handler.postDelayed(object : Runnable {
            override fun run() {
                time = mediaPlayer?.currentPosition?.getTimeFromDuration().toString()
                handler.postDelayed(this, 500)
                EventBus.getDefault().post(MessageEventMusic(time, Constant.EVENT_UPDATE_TIME))
            }
        }, 100)

    }


    fun playMusic() {
        mediaPlayer?.let { mp ->
            if (mp.isPlaying) {
                mp.pause()
            } else {
                mp.start()
            }
            EventBus.getDefault().post(MessageEventMusic(Constant.EVENT_PLAY_MUSIC, mp.isPlaying))
        }
    }

    fun isPlaying() = try {
        mediaPlayer?.isPlaying ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }


    fun stopMusic() {
        runCatching {
            mediaPlayer?.let {
                it.stop()
                it.release()
            }
            mediaPlayer = null
        }.onFailure { it.printStackTrace() }

    }
}