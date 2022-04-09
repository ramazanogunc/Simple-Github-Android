package com.ramo.simplegithub.data.remote

import android.util.Log
import com.ramo.simplegithub.data.remote.model.UserListRequest
import com.ramo.simplegithub.data.remote.model.response.UserListResponse
import com.ramo.simplegithub.domain.model.User
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
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
        val response = networkClient.client.get("https://api.github.com/$SEARCH_USER") {
            parameter("q", req.query)
            parameter("page", req.page.toString())
            parameter("per_page", req.perPage.toString())
        }.body<UserListResponse>()
        //Log.d("TAG", "getUserList: "+response.toString())
        return response.toUserList()
        //return listOf()
    }
}