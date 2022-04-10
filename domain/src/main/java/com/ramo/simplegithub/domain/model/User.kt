package com.ramo.simplegithub.domain.model

data class User(
    val id: Int,
    val userName: String,
    val profileUrl: String,
    val profilePictureUrl: String,
    val follower: Int = 0,
    val following: Int = 0,
    val bio: String = "",
    val company: String = "",
    val location: String = "",
    val publicRepos: Int = 0,
    var isFavorite: Boolean? = null
)
