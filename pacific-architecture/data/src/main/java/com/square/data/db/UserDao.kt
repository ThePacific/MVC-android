package com.square.data.db

import androidx.room.Dao
import androidx.room.Query
import com.square.data.db.entities.DbUser

@Dao
interface UserDao : AppDao<DbUser> {

    @Query("SELECT * FROM user WHERE userId =:userId")
    fun get(userId: Int): DbUser?
}