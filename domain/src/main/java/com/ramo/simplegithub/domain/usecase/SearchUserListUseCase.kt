package com.ramo.simplegithub.domain.usecase

import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String, page: Int, per_page: Int): List<User> =
        userRepository.searchUserList(query, page, per_page)
}