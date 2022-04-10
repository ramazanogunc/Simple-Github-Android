package com.ramo.simplegithub.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.simplegithub.data.remote.model.response.UserDetailResponse

@Dao
interface UserDetailResponseDao : BaseDao<UserDetailResponse> {

    @Query("DELETE FROM user_response WHERE id IN (:ids)")
    suspend fun deleteAllById(ids: List<Int>)

    @Query("UPDATE user_detail_response SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun changeFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM user_detail_response WHERE id=:id")
    suspend fun get(id: Int): UserDetailResponse

}