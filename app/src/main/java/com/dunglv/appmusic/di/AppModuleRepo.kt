package com.dunglv.appmusic.di

import android.app.Application
import com.dunglv.appmusic.data.local.room.dao.DaoHeart
import com.dunglv.appmusic.data.local.room.dao.DaoHeartAdd
import com.dunglv.appmusic.data.local.room.dao.DaoItemPlayList
import com.dunglv.appmusic.data.local.room.dao.DaoMusic
import com.dunglv.appmusic.data.local.room.dao.DaoMusicAdd
import com.dunglv.appmusic.data.local.room.dao.DaoMusicPL
import com.dunglv.appmusic.data.local.room.dao.DaoRecently
import com.dunglv.appmusic.data.local.room.dao.DaoRecentlyAdd
import com.dunglv.appmusic.data.repository.album.AlbumRepo
import com.dunglv.appmusic.data.repository.album.AlbumRepoImpl
import com.dunglv.appmusic.data.repository.foder.FolderRepo
import com.dunglv.appmusic.data.repository.foder.FolderRepoImpl
import com.dunglv.appmusic.data.repository.heart.HeartRepo
import com.dunglv.appmusic.data.repository.heart.HeartRepoImpl
import com.dunglv.appmusic.data.repository.heartadd.HeartAddRepo
import com.dunglv.appmusic.data.repository.heartadd.HeartAddRepoImpl
import com.dunglv.appmusic.data.repository.itemplay.ItemPlayRepo
import com.dunglv.appmusic.data.repository.itemplay.ItemPlayRepoImpl
import com.dunglv.appmusic.data.repository.language.LanguageRepo
import com.dunglv.appmusic.data.repository.language.LanguageRepoImpl
import com.dunglv.appmusic.data.repository.music.MusicRepo
import com.dunglv.appmusic.data.repository.music.MusicRepoImpl
import com.dunglv.appmusic.data.repository.musicAdd.MusicAddRepo
import com.dunglv.appmusic.data.repository.musicAdd.MusicAddRepoImpl
import com.dunglv.appmusic.data.repository.musicPlayList.MusicPLRepo
import com.dunglv.appmusic.data.repository.musicPlayList.MusicPLRepoImpl
import com.dunglv.appmusic.data.repository.recent.RecentRepo
import com.dunglv.appmusic.data.repository.recent.RecentRepoImpl
import com.dunglv.appmusic.data.repository.recentAdd.RecentAddRepo
import com.dunglv.appmusic.data.repository.recentAdd.RecentAddRepoImpl
import com.dunglv.appmusic.data.repository.singer.SingRepo
import com.dunglv.appmusic.data.repository.singer.SingRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModuleRepo {

    @Provides
    @Singleton
    fun provideRecentRepo(dao: DaoRecently): RecentRepo = RecentRepoImpl(dao)

    @Provides
    @Singleton
    fun provideRecentAddRepo(dao: DaoRecentlyAdd): RecentAddRepo = RecentAddRepoImpl(dao)

    @Provides
    @Singleton
    fun provideHeartRepo(dao: DaoHeart): HeartRepo = HeartRepoImpl(dao)

    @Provides
    @Singleton
    fun provideHeartAddRepo(dao: DaoHeartAdd): HeartAddRepo = HeartAddRepoImpl(dao)

    @Provides
    @Singleton
    fun provideMusicAddRepo(dao: DaoMusicAdd): MusicAddRepo = MusicAddRepoImpl(dao)

    @Provides
    @Singleton
    fun providePlayListRepo(dao: DaoItemPlayList): ItemPlayRepo = ItemPlayRepoImpl(dao)

    @Provides
    @Singleton
    fun provideMusic(dao: DaoMusic, application: Application): MusicRepo =
        MusicRepoImpl(dao, application)

    @Provides
    @Singleton
    fun provideMusicPlayList(daoMusicPL: DaoMusicPL): MusicPLRepo = MusicPLRepoImpl(daoMusicPL)

    @Provides
    @Singleton
    fun provideSinger(application: Application): SingRepo = SingRepoImpl(application)

    @Provides
    @Singleton
    fun provideAlbum(application: Application): AlbumRepo = AlbumRepoImpl(application)

    @Provides
    @Singleton
    fun provideFolder(application: Application): FolderRepo = FolderRepoImpl(application)

    @Provides
    @Singleton
    fun provideLanguage(application: Application): LanguageRepo = LanguageRepoImpl(application)
}


