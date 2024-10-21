package com.dunglv.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.PrimaryKey

data class Album (
    var name: String ,
    var nameSing: String ,
    var imageBitmap: Bitmap? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long=0
)