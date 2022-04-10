package com.ramo.simplegithub.data.repository

import com.ramo.simplegithub.data.NetworkUtil
import com.ramo.simplegithub.data.local.dao.FavoriteUserDao
import com.ramo.simplegithub.data.local.dao.UserResponseDao
import com.ramo.simplegithub.data.local.model.FavoriteUser
import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.data.remote.model.response.UserResponse
import com.ramo.simplegithub.domain.exception.RefreshException
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val userResponseDao: UserResponseDao,
    private val favoriteUserDao: FavoriteUserDao,
    networkUtil: NetworkUtil
) : BaseRepository(networkUtil), UserRepository {

    override suspend fun getUserList(page: Int, perPage: Int): List<User> {
        val local = userResponseDao.getListByPaging(page, perPage)
        checkStatus(local)
        if (isOnlyCache) return local.map { it.toUser() }
        val response = githubService.getUserList(page, perPage)
        saveUserListCache(response.items)
        return userResponseDao.getListByPaging(page, perPage).map { it.toUser() }
    }

    override suspend fun searchUserList(query: String, page: Int, perPage: Int): List<User> {
        if (isOnlyCache) throw RefreshException()
        val response = githubService.searchUserList(query, page, perPage)
        saveUserListCache(response.items)
        return githubService.searchUserList(query, page, perPage).toUserList()
    }

    override suspend fun getUserDetail(userName: String): User {
        return githubService.getUserDetail(userName).toUser()
    }

    override suspend fun changeFavoriteStatus(user: User, isFavorite: Boolean) {
        user.isFavorite = isFavorite
        if (isFavorite)
            favoriteUserDao.insert(FavoriteUser(user.id))
        else
            favoriteUserDao.delete(FavoriteUser(user.id))
        userResponseDao.changeFavoriteStatus(user.id, isFavorite)
        // TODO: Change user detail cache
    }

    private suspend fun saveUserListCache(items: List<UserResponse>) {
        userResponseDao.deleteAllById(items.map { it.id ?: 0 })
        userResponseDao.insertAll(items)
        val favoriteUsersIds = favoriteUserDao.getAll().map { it.id }
        userResponseDao.changeFavoriteStatus(favoriteUsersIds, true)
    }
}