package com.dunglv.appmusic.data.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.dunglv.appmusic.data.local.room.dao.DaoHeart
import com.dunglv.appmusic.data.local.room.dao.DaoHeartAdd
import com.dunglv.appmusic.data.local.room.dao.DaoItemPlayList
import com.dunglv.appmusic.data.local.room.dao.DaoMusic
import com.dunglv.appmusic.data.local.room.dao.DaoMusicAdd
import com.dunglv.appmusic.data.local.room.dao.DaoMusicPL
import com.dunglv.appmusic.data.local.room.dao.DaoRecently
import com.dunglv.appmusic.data.local.room.dao.DaoRecentlyAdd

@Entity
data class ItemPlayList(
    @ColumnInfo
    var name: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
){
    fun insertPlayList(daoItemPlayList: DaoItemPlayList) = daoItemPlayList.insertPlayList(this)
}

@Entity
data class Music(
    @ColumnInfo var nameSong: String,
    @ColumnInfo var nameSing: String,
    @ColumnInfo var album: String?,
    @ColumnInfo var uri: String?,
    @ColumnInfo var duration: Long?,
    @ColumnInfo var nameFolder: String?,
    var isSelect: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
) {
    @Ignore
    var imageBitmap: Bitmap? = null
    @Ignore
    var songPosition: Int = 0
    @Ignore
    var isHeart: Boolean = false

    @Ignore
    fun insertMusic(daoMusic: DaoMusic) = daoMusic.insert(this)
    override fun toString(): String {
        return "Music(nameSong='$nameSong', imageBitmap=$imageBitmap)\n"
    }
}

fun Music.copyModel(): Music {
    val copiedMusic = Music(nameSong, nameSing, album, uri, duration, nameFolder, isSelect, id)
    copiedMusic.imageBitmap = this.imageBitmap
    copiedMusic.songPosition = this.songPosition
    copiedMusic.isHeart = this.isHeart
    return copiedMusic
}
@Entity
data class Heart(
    @ColumnInfo var uri: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    fun insertMusicToRoomDB(daoHeart: DaoHeart) = daoHeart.insert(this)
}


@Entity
data class Recently(
    @ColumnInfo var uri: String?){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    fun insertRecently(daoRecently: DaoRecently) = daoRecently.insertRecently(this)
}


@Entity
data class MusicAdd(
    @ColumnInfo var uri: String?,
    @ColumnInfo var idPlayList: Long){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    fun insertMusicAdd(daoMusicAdd: DaoMusicAdd) = daoMusicAdd.insertAdd(this)
}
@Entity
data class MusicPlayList(
    @ColumnInfo var uri: String?,
    @ColumnInfo var idPlayList: Long){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    fun insertMusicPL(daoMusicPL: DaoMusicPL) = daoMusicPL.insertPlayList(this)
}

@Entity
data class RecentlyAdd(
    @ColumnInfo var uri: String?,
    @ColumnInfo var idPlayList: Long){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun insertRecentlyAdd(daoRecentlyAdd: DaoRecentlyAdd) = daoRecentlyAdd.insertAddRecently(this)
}

@Entity
data class HeartAdd(
    @ColumnInfo var uri: String?,
    @ColumnInfo var idPlayList: Long){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun insertHeartAdd(daoHeartAdd: DaoHeartAdd) = daoHeartAdd.insertAddHeart(this)
}
data class Singer(
    var nameSinger: String?,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)

data class Folder(
    var nameFolder: String?,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)