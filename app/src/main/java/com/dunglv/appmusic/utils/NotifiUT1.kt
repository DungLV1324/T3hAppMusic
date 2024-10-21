package com.dunglv.appmusic.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.dunglv.appmusic.common.Constant
import com.dunglv.appmusic.service.broadcas.MyReceiver
import com.dunglv.appmusic.ui.MainActivity
import com.dunglv.appmusickl.R

class NotifiUT1(val context: Context) {
    private val CHANNEL_ID = "MUSIC_SERVICE"
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var notId = 1
    private var pathMusic: String = ""
    private var mediaPlayerNoti: MediaPlayer? = null
    var nameSong = ""
    var nameSing = ""

    fun intent() = Intent(context, MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        putExtra(Constant.KEY_URI_MUSIC, pathMusic)
        putExtra(Constant.KEY_START_FROM_AUDIO_SERVICE, true)
        try {
            putExtra(Constant.KEY_MUSIC_IS_PLAYING, mediaPlayerNoti?.isPlaying ?: false)
            putExtra(Constant.KEY_PLAY_MUSIC, mediaPlayerNoti?.duration ?: 0)

        } catch (e: Exception) {

        }
    }

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_music1)
    private val mediaSessionCompat = MediaSessionCompat(context, "tag")
    private var notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setSmallIcon(R.drawable.ic_music1)
        .setLargeIcon(bitmap)
        .setOngoing(true)
        .addAction(
            R.drawable.ic_com_back_bl,
            context.getString(R.string.previous),
            getPendingIntent(context, Constant.BRO_COMEBACK_MUSIC)
        )
        .addAction(
            R.drawable.ic_play1,
            context.getString(R.string.pause),
            getPendingIntent(context, Constant.BRO_PAUSE_MUSIC)
        )
        .addAction(
            R.drawable.ic_next_bl,
            context.getString(R.string.next),
            getPendingIntent(context, Constant.BRO_NEXT_MUSIC)
        ).setSubText( context.getString(R.string.music))
        .setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1)
                .setMediaSession(mediaSessionCompat.sessionToken)
        )

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = name
                }
            notificationManager.createNotificationChannel(channel)
        }
        getTokenFromSharedPreferences(context)?.let { path ->
            pathMusic = path
        }
        notificationBuilder.setContentIntent(
            setContent()
        )

    }

    fun setName(nameSong: String, naneSing: String) {
        notificationBuilder
            .setContentTitle(nameSong)
            .setContentText(naneSing)
        val a = notificationBuilder.build()
        notificationManager.notify(notId, a)
    }

    fun setActionPause() {
        notificationBuilder
            .clearActions()
            .addAction(
                R.drawable.ic_com_back_bl,
                context.getString(R.string.previous),
                getPendingIntent(context, Constant.BRO_COMEBACK_MUSIC)
            )
            .addAction(
                R.drawable.ic_play1,
                context.getString(R.string.pause),
                getPendingIntent(context, Constant.BRO_PAUSE_MUSIC)
            )
            .addAction(
                R.drawable.ic_next_bl,
                context.getString(R.string.next),
                getPendingIntent(context, Constant.BRO_NEXT_MUSIC)
            )

        val a = notificationBuilder.build()
        notificationManager.notify(notId, a)
    }

    fun setActionNext() {
        notificationBuilder
            .clearActions()
            .addAction(
                R.drawable.ic_com_back_bl,
                context.getString(R.string.previous),
                getPendingIntent(context, Constant.BRO_COMEBACK_MUSIC)
            )
            .addAction(
                R.drawable.ic_play1,
                context.getString(R.string.pause),
                getPendingIntent(context, Constant.BRO_PAUSE_MUSIC)
            )
            .addAction(
                R.drawable.ic_next_bl,
                context.getString(R.string.next),
                getPendingIntent(context, Constant.BRO_NEXT_MUSIC)
            )

        val a = notificationBuilder.build()
        notificationManager.notify(notId, a)
    }

    fun setActionPlay() {
        notificationBuilder
            .clearActions()
            .addAction(R.drawable.ic_com_back_bl, context.getString(R.string.previous),
                getPendingIntent(context, Constant.BRO_COMEBACK_MUSIC)
            )
            .addAction(
                R.drawable.ic_pause1,  context.getString(R.string.play),
                getPendingIntent(context, Constant.BRO_PLAY_MUSIC)
            )
            .addAction(R.drawable.ic_next_bl,  context.getString(R.string.next),
                getPendingIntent(context, Constant.BRO_NEXT_MUSIC)
            )
        val a = notificationBuilder.build()
        notificationManager.notify(notId, a)
    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent {
        Intent(context, MyReceiver::class.java).apply {
            putExtra(Constant.KEY_ACTION_CLICK_NOTI, action)
            return PendingIntent.getBroadcast(
                context.applicationContext,
                action,
                this,
                PendingIntent.FLAG_MUTABLE
            )
        }
    }


    private fun setContent() =
        PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent(),
            PendingIntent.FLAG_MUTABLE
        )

    fun getNotification(): Notification {
        val a = notificationBuilder.build()
        notificationManager.notify(notId, a)
        return a
    }

    private fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MUSIC_STRING, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constant.KEY, "0")
    }
}