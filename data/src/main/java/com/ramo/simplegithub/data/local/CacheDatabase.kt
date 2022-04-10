package com.ramo.simplegithub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ramo.simplegithub.data.local.dao.FavoriteUserDao
import com.ramo.simplegithub.data.local.dao.UserDetailResponseDao
import com.ramo.simplegithub.data.local.dao.UserResponseDao
import com.ramo.simplegithub.data.local.model.FavoriteUser
import com.ramo.simplegithub.data.remote.model.response.UserDetailResponse
import com.ramo.simplegithub.data.remote.model.response.UserResponse

@Database(
    entities = [
        UserResponse::class,
        FavoriteUser::class,
        UserDetailResponse::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CacheDatabase : RoomDatabase() {
    abstract val userResponseDao: UserResponseDao
    abstract val favoriteUserDao: FavoriteUserDao
    abstract val userDetailResponseDao: UserDetailResponseDao

    companion object {
        @Volatile
        private var INSTANCE: CacheDatabase? = null

        fun getDatabase(context: Context): CacheDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CacheDatabase::class.java,
                "cache_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}