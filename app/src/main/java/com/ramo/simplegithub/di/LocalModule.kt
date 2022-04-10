package com.ramo.simplegithub.di

import android.content.Context
import com.ramo.simplegithub.data.local.CacheDatabase
import com.ramo.simplegithub.data.local.dao.FavoriteUserDao
import com.ramo.simplegithub.data.local.dao.UserResponseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesCacheDatabase(
        @ApplicationContext context: Context
    ): CacheDatabase = CacheDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesUserResponseDao(
        cacheDatabase: CacheDatabase
    ): UserResponseDao = cacheDatabase.userResponseDao

    @Singleton
    @Provides
    fun providesFavoriteUserDao(
        cacheDatabase: CacheDatabase
    ): FavoriteUserDao = cacheDatabase.favoriteUserDao


}