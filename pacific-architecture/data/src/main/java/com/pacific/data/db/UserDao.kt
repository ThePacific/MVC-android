package com.pacific.data.db

import androidx.room.Dao
import androidx.room.Query
import com.pacific.data.db.entities.DbUser

@Dao
interface UserDao :
    AppDao<DbUser> {

    @Query("SELECT * FROM user WHERE userId =:userId")
    fun get(userId: Int): DbUser?
}