package com.ramo.simplegithub.di

import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.data.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesGithubService(): GithubService = GithubService(NetworkClient)
}