package com.ramo.simplegithub.di

import com.ramo.simplegithub.domain.repository.UserRepository
import com.ramo.simplegithub.domain.usecase.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesUserListUseCase(userRepository: UserRepository): UserListUseCase =
        UserListUseCase(userRepository)

}