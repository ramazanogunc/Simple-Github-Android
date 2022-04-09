package com.ramo.simplegithub.data.remote.model.response

import com.google.gson.annotations.SerializedName
import com.ramo.simplegithub.domain.model.User

data class UserListResponse(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: ArrayList<UserResponse> = arrayListOf()
) {
    fun toUserList(): List<User> = items.map { it.toUser() }
}
