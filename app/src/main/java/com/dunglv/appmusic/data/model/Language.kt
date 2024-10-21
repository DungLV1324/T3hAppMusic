package com.dunglv.appmusic.data.model

import android.graphics.drawable.Drawable
import androidx.room.PrimaryKey

data class Language(
    var name: String?,
    var code: String?,
    var image: Drawable? = null,
    var isSelect: Boolean = false
)