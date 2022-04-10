package com.ramo.simplegithub.domain.usecase

import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserChangeFavoriteStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, isFavorite: Boolean) =
        userRepository.changeFavoriteStatus(user, isFavorite)
}