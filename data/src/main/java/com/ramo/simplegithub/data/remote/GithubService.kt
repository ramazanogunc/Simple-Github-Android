package com.ramo.simplegithub.data.remote

import com.ramo.simplegithub.data.remote.model.UserListRequest
import com.ramo.simplegithub.data.remote.model.response.UserListResponse
import com.ramo.simplegithub.domain.model.User
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class GithubService @Inject constructor(
    private val networkClient: NetworkClient
) {

    companion object {
        val SEARCH_USER = "search/users"
    }

    suspend fun getUserList(page: Int, perPage: Int): List<User> {
        val req = UserListRequest(
            page = page,
            perPage = perPage
        )
        val response = networkClient.client.get(SEARCH_USER) {
            parameter("q", req.query)
            parameter("page", req.page.toString())
            parameter("per_page", req.perPage.toString())
        }.body<UserListResponse>()
        return response.toUserList()
    }


    suspend fun searchUserList(query: String, page: Int, perPage: Int): List<User> {
        val req = UserListRequest(
            page = page,
            perPage = perPage
        )
        val response = networkClient.client.get(SEARCH_USER) {
            parameter("q", query)
            parameter("page", req.page.toString())
            parameter("per_page", req.perPage.toString())
        }.body<UserListResponse>()
        return response.toUserList()
    }
}