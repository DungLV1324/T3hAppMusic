package com.dunglv.appmusic.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.dunglv.appmusic.data.local.room.appdatabase.AppDataBase
import com.dunglv.appmusic.data.local.room.dao.DaoHeart
import com.dunglv.appmusic.data.local.room.dao.DaoHeartAdd
import com.dunglv.appmusic.data.local.room.dao.DaoItemPlayList
import com.dunglv.appmusic.data.local.room.dao.DaoMusic
import com.dunglv.appmusic.data.local.room.dao.DaoMusicAdd
import com.dunglv.appmusic.data.local.room.dao.DaoMusicPL
import com.dunglv.appmusic.data.local.room.dao.DaoRecently
import com.dunglv.appmusic.data.local.room.dao.DaoRecentlyAdd
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreference(context: Application?): SharedPreferences
    = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideRoomDb3(context: Application): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "appDataBase").build()

    @Provides
    @Singleton
    fun provideMessageThreadDao1(db: AppDataBase): DaoMusic = db.daoMusic()

    @Provides
    @Singleton
    fun provideMessageThreadDao2(db: AppDataBase): DaoRecently = db.daoRecently()

    @Provides
    @Singleton
    fun provideMessageThreadDao3(db: AppDataBase): DaoItemPlayList = db.daoPlayList()

    @Provides
    @Singleton
    fun provideMessageThreadDao4(db: AppDataBase): DaoMusicAdd = db.daoMusicAdd()

    @Provides
    @Singleton
    fun provideMessageThreadDao5(db: AppDataBase): DaoRecentlyAdd = db.daoRecentlyAdd()

    @Provides
    @Singleton
    fun provideMessageThreadDao6(db: AppDataBase): DaoHeart = db.daoHeart()

    @Provides
    @Singleton
    fun provideMessageThreadDao7(db: AppDataBase): DaoHeartAdd = db.daoHeartAdd()

    @Provides
    @Singleton
    fun provideMessageThreadDao8(db: AppDataBase): DaoMusicPL = db.daoMusicPlayList()
}


