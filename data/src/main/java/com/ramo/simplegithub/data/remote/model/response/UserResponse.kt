package com.ramo.simplegithub.data.remote.model.response

import com.google.gson.annotations.SerializedName
import com.ramo.simplegithub.domain.model.User

data class UserResponse(
    @SerializedName("login") var login: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("node_id") var nodeId: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("gravatar_id") var gravatarId: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
    @SerializedName("followers_url") var followersUrl: String? = null,
    @SerializedName("following_url") var followingUrl: String? = null,
    @SerializedName("gists_url") var gistsUrl: String? = null,
    @SerializedName("starred_url") var starredUrl: String? = null,
    @SerializedName("subscriptions_url") var subscriptionsUrl: String? = null,
    @SerializedName("organizations_url") var organizationsUrl: String? = null,
    @SerializedName("repos_url") var reposUrl: String? = null,
    @SerializedName("events_url") var eventsUrl: String? = null,
    @SerializedName("received_events_url") var receivedEventsUrl: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("site_admin") var siteAdmin: Boolean? = null,
    @SerializedName("score") var score: Int? = null
) {
    fun toUser(): User = User(
        id = id?.toLong() ?: 0L,
        userName = login ?: "",
        profileUrl = htmlUrl ?: "",
        profilePictureUrl = avatarUrl ?: "",
        isFavorite = null
    )
}