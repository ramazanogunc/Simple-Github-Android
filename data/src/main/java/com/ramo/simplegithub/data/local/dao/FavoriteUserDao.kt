package com.ramo.simplegithub.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ramo.simplegithub.data.local.model.FavoriteUser

@Dao
interface FavoriteUserDao : BaseDao<FavoriteUser> {

    @Query("SELECT * FROM favorite_user")
    suspend fun getAll(): List<FavoriteUser>
}