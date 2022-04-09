package com.ramo.simplegithub.domain.usecase

import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): User =
        userRepository.getUserDetail(username)
}