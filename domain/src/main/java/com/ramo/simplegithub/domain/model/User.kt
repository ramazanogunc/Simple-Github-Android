package com.ramo.simplegithub.domain.model

data class User(
    val id: Long,
    val userName: String,
    val profileUrl: String,
    val profilePictureUrl: String,
    var isFavorite: Boolean? = null
)
