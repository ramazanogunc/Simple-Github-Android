package com.ramo.simplegithub.data.remote.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import com.ramo.simplegithub.domain.model.User

@IgnoreExtraProperties
@Entity(tableName = "user_detail_response")
data class UserDetailResponse(
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
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = null,
    @SerializedName("company")
    @ColumnInfo(name = "company")
    var company: String? = null,
    @SerializedName("blog")
    @ColumnInfo(name = "blog")
    var blog: String? = null,
    @SerializedName("location")
    @ColumnInfo(name = "location")
    var location: String? = null,
    @SerializedName("email")
    @ColumnInfo(name = "email")
    var email: String? = null,
    @SerializedName("bio")
    @ColumnInfo(name = "bio")
    var bio: String? = null,
    @SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    var publicRepos: Int? = null,
    @SerializedName("public_gists")
    @ColumnInfo(name = "public_gists")
    var publicGists: Int? = null,
    @SerializedName("followers")
    @ColumnInfo(name = "followers")
    var followers: Int? = null,
    @SerializedName("following")
    @ColumnInfo(name = "following")
    var following: Int? = null,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) {
    fun toUser(): User = User(
        id = id ?: 0,
        userName = login ?: "",
        profileUrl = htmlUrl ?: "",
        profilePictureUrl = avatarUrl ?: "",
        bio = bio ?: "",
        location = location ?: "",
        company = company ?: "",
        follower = followers ?: 0,
        following = following ?: 0,
        publicRepos = publicRepos ?: 0,
        isFavorite = isFavorite
    )
}