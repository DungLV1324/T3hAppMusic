package com.dunglv.appmusic.common

object Constant {

    //Service
    const val KEY_ACTION_CLICK_NOTI = "KEY_ACTION_CLICK_NOTI"



    const val KEY_NAME = "KEY_NAME"
    const val KEY_ID = "KEY_ID"
    const val KEY_URI_MUSIC_CURRENT = "KEY_DATA"
    const val KEY_PLAY_LIST = "KEY_PLAY_LIST"
    const val KEY_START_MUSIC = "KEY_START_MUSIC"
    const val KEY_URI_MUSIC = "KEY_URI_MUSIC"
    const val KEY_PLAY_MUSIC = "KEY_PLAY_MUSIC"
    const val KEY_NEXT_MUSIC = "KEY_NEXT_MUSIC"
    const val KEY_COMEBACK_MUSIC = "KEY_COMEBACK_MUSIC"
    const val KEY_SEEK_TO_MUSIC = "KEY_SEEK_TO_MUSIC"

    //Intent
    const val KEY_PATH_MUSIC = "KEY_PATH_MUSIC"
    const val KEY_START_FROM_AUDIO_SERVICE = "KEY_START_FROM_AUDIO_SERVICE"
    const val KEY_MUSIC_IS_PLAYING = "KEY_MUSIC_IS_PLAYING"
    const val KEY_DURATION_MUSIC = "KEY_DURATION_MUSIC"
    const val KEY_CURRENT_POSITION_MUSIC = "KEY_CURRENT_POSITION_MUSIC"

//broadcas
    const val BRO_PLAY_MUSIC = 1
    const val BRO_PAUSE_MUSIC = 4
    const val BRO_NEXT_MUSIC = 2
    const val BRO_COMEBACK_MUSIC = 3
    const val BRO_SEEK_MUSIC = 5

    const val TYPE = "1234"
    const val KEY = "KEY"
    const val PLAYLIST = "PLAYLIST"
    const val MUSIC = "MUSIC"
    const val MUSIC_STRING = "MUSIC_STRING"
    const val KEY_URI_PLAY_LIST = "KEY_URI_PLAY_LIST"
    const val KEY_URI = "KEY_URI"
    const val KEY_POS = "KEY_POS"
    const val KEY_POS_GET = "KEY_POS_GET"


    //Eventbus
    const val EVENT_PLAY_MUSIC = 1
    const val EVENT_START_MUSIC = 2
    const val SEEK_TO_MUSIC = "SEEK_TO_MUSIC"
    const val CURRENT_TO_MUSIC = "CURRENT_TO_MUSIC"
    const val EVENT_UPDATE_TIME = 3


    //Service
    const val KEY_ONLY_LOOP = 1
    const val KEY_RANDOM_LOOP = 2
    const val KEY_ALL_LOOP = 0
}