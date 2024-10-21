package com.dunglv.appmusic.data.local.room.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dunglv.appmusic.data.local.room.dao.DaoHeart
import com.dunglv.appmusic.data.local.room.dao.DaoHeartAdd
import com.dunglv.appmusic.data.local.room.dao.DaoItemPlayList
import com.dunglv.appmusic.data.local.room.dao.DaoMusic
import com.dunglv.appmusic.data.local.room.dao.DaoMusicAdd
import com.dunglv.appmusic.data.local.room.dao.DaoMusicPL
import com.dunglv.appmusic.data.local.room.dao.DaoRecently
import com.dunglv.appmusic.data.local.room.dao.DaoRecentlyAdd
import com.dunglv.appmusic.data.model.Heart
import com.dunglv.appmusic.data.model.HeartAdd
import com.dunglv.appmusic.data.model.ItemPlayList
import com.dunglv.appmusic.data.model.Music
import com.dunglv.appmusic.data.model.MusicAdd
import com.dunglv.appmusic.data.model.MusicPlayList
import com.dunglv.appmusic.data.model.Recently
import com.dunglv.appmusic.data.model.RecentlyAdd

@Database(
    entities = [Music::class, Recently::class, ItemPlayList::class, MusicAdd::class,
        RecentlyAdd::class,MusicPlayList::class,Heart::class,HeartAdd::class],
    version = 1,
    exportSchema = false
)
abstract class  AppDataBase : RoomDatabase() {
    abstract fun daoMusic(): DaoMusic
    abstract fun daoRecently(): DaoRecently
    abstract fun daoHeart(): DaoHeart
    abstract fun daoRecentlyAdd(): DaoRecentlyAdd
    abstract fun daoPlayList(): DaoItemPlayList
    abstract fun daoMusicAdd(): DaoMusicAdd
    abstract fun daoHeartAdd(): DaoHeartAdd
    abstract fun daoMusicPlayList(): DaoMusicPL
}