package com.ramo.simplegithub.domain.repository

import com.ramo.simplegithub.domain.model.User

interface UserRepository {
    suspend fun getUserList(page: Int, perPage: Int): List<User>
}