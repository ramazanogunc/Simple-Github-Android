package com.ramo.simplegithub.domain.usecase

import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, per_page: Int): List<User> =
        userRepository.getUserList(page, per_page)
}