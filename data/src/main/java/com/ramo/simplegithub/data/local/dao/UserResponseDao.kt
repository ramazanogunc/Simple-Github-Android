package com.ramo.simplegithub.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.simplegithub.data.remote.model.response.UserResponse

@Dao
interface UserResponseDao : BaseDao<UserResponse> {

    @Query("DELETE FROM user_response")
    suspend fun deleteAll()

    @Query("DELETE FROM user_response WHERE id IN (:ids)")
    suspend fun deleteAllById(ids: List<Int>)

    @Query("UPDATE user_response SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun changeFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("UPDATE user_response SET isFavorite=:isFavorite WHERE id IN (:id)")
    suspend fun changeFavoriteStatus(id: List<Int>, isFavorite: Boolean)

    @Query("SELECT * FROM user_response LIMIT ((:page-1)*:perPage), :perPage")
    suspend fun getListByPaging(page: Int, perPage: Int): List<UserResponse>

    @Query("SELECT * FROM user_response WHERE login  LIKE '%' || :query || '%'  LIMIT ((:page-1)*:perPage), :perPage")
    suspend fun searchListByPaging(query: String, page: Int, perPage: Int): List<UserResponse>
}