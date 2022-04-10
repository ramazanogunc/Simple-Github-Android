package com.ramo.simplegithub.di

import com.ramo.simplegithub.data.NetworkUtil
import com.ramo.simplegithub.data.local.CacheDatabase
import com.ramo.simplegithub.data.local.dao.FavoriteUserDao
import com.ramo.simplegithub.data.local.dao.UserResponseDao
import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.data.repository.UserRepositoryImpl
import com.ramo.simplegithub.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesUserRepository(
        githubService: GithubService,
        cacheDatabase: CacheDatabase,
        networkUtil: NetworkUtil
    ): UserRepository = UserRepositoryImpl(
        githubService,
        cacheDatabase,
        networkUtil
    )

}