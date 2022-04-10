package com.ramo.simplegithub.data.remote.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ramo.simplegithub.data.remote.model.response.UserDetailResponse
import com.ramo.simplegithub.data.remote.model.response.UserResponse


class FirebaseCache {
    private val database = Firebase.database
    private val refUserList = database.getReference("user_list")
    private val refUserDetail = database.getReference("user_response_list")


    suspend fun saveUserList(list: List<UserResponse>) {
        list.forEach { item ->
            refUserList.child(item.id.toString()).setValue(item)
        }
    }

    suspend fun saveUserDetail(userDetail: UserDetailResponse) {
        refUserDetail.child(userDetail.id.toString()).setValue(userDetail)
    }

    suspend fun changeFavoriteStatus(userId: Int, isFavorite:Boolean){
        refUserDetail.child(userId.toString()).child("favorite").setValue(isFavorite)
        refUserList.child(userId.toString()).child("favorite").setValue(isFavorite)
    }
}