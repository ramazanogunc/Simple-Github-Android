package com.ramo.simplegithub.data.remote

import com.ramo.simplegithub.data.remote.model.response.UserDetailResponse
import com.ramo.simplegithub.data.remote.model.response.UserListResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class GithubService @Inject constructor(
    private val networkClient: NetworkClient
) {

    companion object {
        val SEARCH_USER = "search/users"
        val USER_DETAIL = "users/"
    }

    suspend fun getUserList(page: Int, perPage: Int): UserListResponse =
        networkClient.client.get(SEARCH_USER) {
            parameter("q", "type:user")
            parameter("page", page)
            parameter("per_page", perPage)
        }.body()


    suspend fun searchUserList(query: String, page: Int, perPage: Int): UserListResponse =
        networkClient.client.get(SEARCH_USER) {
            parameter("q", query)
            parameter("page", page)
            parameter("per_page", perPage)
        }.body()

    suspend fun getUserDetail(userName: String): UserDetailResponse =
        networkClient.client.get(USER_DETAIL + userName).body()
}