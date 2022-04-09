package com.ramo.simplegithub.data.repository

import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService
): UserRepository {

    override suspend fun getUserList(page: Int, perPage: Int): List<User> {
        return githubService.getUserList(page,perPage)
    }
}