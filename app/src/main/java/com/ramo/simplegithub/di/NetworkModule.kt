package com.ramo.simplegithub.di

import android.content.Context
import com.ramo.simplegithub.data.NetworkUtil
import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.data.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesGithubService(): GithubService = GithubService(NetworkClient)

    @Singleton
    @Provides
    fun providesNetworkUtil(
        @ApplicationContext context: Context
    ): NetworkUtil = NetworkUtil(context)
}