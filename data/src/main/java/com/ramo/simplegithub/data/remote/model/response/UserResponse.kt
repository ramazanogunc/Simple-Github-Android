package com.ramo.simplegithub.data.remote.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import com.ramo.simplegithub.domain.model.User

@IgnoreExtraProperties
@Entity(tableName = "user_response")
data class UserResponse(
    @SerializedName("login")
    @ColumnInfo(name = "login")
    var login: String? = null,
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null,
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) {
    fun toUser(): User = User(
        id = id ?: 0,
        userName = login ?: "",
        profileUrl = htmlUrl ?: "",
        profilePictureUrl = avatarUrl ?: "",
        isFavorite = isFavorite
    )
}