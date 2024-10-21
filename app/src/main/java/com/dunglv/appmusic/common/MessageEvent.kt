package com.dunglv.appmusic.common

import android.media.MediaPlayer
import android.widget.SeekBar
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.data.model.Music

class MessageEvent {

    var type: Int = 0
    var playList: List<ItemPlayList> = listOf()


    constructor(type: Int, item: List<ItemPlayList>) {
        this.type = type
        this.playList = item
    }
}