package com.ramo.simplegithub.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserListRequest(
    @SerializedName("q") var query: String = "type:user",
    @SerializedName("page") var page: Int,
    @SerializedName("per_page") var perPage: Int,
)
