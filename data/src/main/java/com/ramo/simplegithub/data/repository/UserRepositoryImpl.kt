package com.ramo.simplegithub.data.repository

import com.ramo.simplegithub.data.NetworkUtil
import com.ramo.simplegithub.data.local.CacheDatabase
import com.ramo.simplegithub.data.local.model.FavoriteUser
import com.ramo.simplegithub.data.remote.GithubService
import com.ramo.simplegithub.data.remote.model.response.UserDetailResponse
import com.ramo.simplegithub.data.remote.model.response.UserResponse
import com.ramo.simplegithub.domain.exception.RefreshException
import com.ramo.simplegithub.domain.model.User
import com.ramo.simplegithub.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val cacheDatabase: CacheDatabase,
    networkUtil: NetworkUtil
) : BaseRepository(networkUtil), UserRepository {

    override suspend fun getUserList(page: Int, perPage: Int): List<User> {
        val local = cacheDatabase.userResponseDao.getListByPaging(page, perPage)
        checkStatus(local)
        if (isOnlyCache) return local.map { it.toUser() }
        val response = githubService.getUserList(page, perPage)
        saveUserListCache(response.items)
        return cacheDatabase.userResponseDao.getListByPaging(page, perPage).map { it.toUser() }
    }

    override suspend fun searchUserList(query: String, page: Int, perPage: Int): List<User> {
        if (isOnlyCache) return cacheDatabase.userResponseDao.searchListByPaging(
            query,
            page,
            perPage
        ).map { it.toUser() }
        val response = githubService.searchUserList(query, page, perPage)
        saveUserListCache(response.items)
        return cacheDatabase.userResponseDao.searchListByPaging(query, page, perPage)
            .map { it.toUser() }
    }

    override suspend fun getUserDetail(userName: String): User {
        val local = cacheDatabase.userDetailResponseDao.getByUsername(userName)
        if (isOnlyCache && local == null) throw RefreshException()
        if (isOnlyCache) return local!!.toUser()
        val response = githubService.getUserDetail(userName)
        saveUserDetailCache(response)
        return cacheDatabase.userDetailResponseDao.get(response.id ?: 0).toUser()
    }

    override suspend fun changeFavoriteStatus(user: User, isFavorite: Boolean) {
        user.isFavorite = isFavorite
        if (isFavorite)
            cacheDatabase.favoriteUserDao.insert(FavoriteUser(user.id))
        else
            cacheDatabase.favoriteUserDao.delete(FavoriteUser(user.id))
        cacheDatabase.userResponseDao.changeFavoriteStatus(user.id, isFavorite)
        cacheDatabase.userDetailResponseDao.changeFavoriteStatus(user.id, isFavorite)
    }

    private suspend fun saveUserListCache(items: List<UserResponse>) {
        val userResponseDao = cacheDatabase.userResponseDao
        userResponseDao.deleteAllById(items.map { it.id ?: 0 })
        userResponseDao.insertAll(items)
        val favoriteUsersIds = cacheDatabase.favoriteUserDao.getAll().map { it.id }
        userResponseDao.changeFavoriteStatus(favoriteUsersIds, true)
    }

    private suspend fun saveUserDetailCache(userDetail: UserDetailResponse) {
        val userDetailResponseDao = cacheDatabase.userDetailResponseDao
        userDetailResponseDao.insert(userDetail)
        val favoriteUsersIds = cacheDatabase.favoriteUserDao.getAll().map { it.id }
        val isFavorite = favoriteUsersIds.any { it == userDetail.id }
        if (isFavorite)
            userDetailResponseDao.changeFavoriteStatus(userDetail.id ?: 0, true)
    }
}