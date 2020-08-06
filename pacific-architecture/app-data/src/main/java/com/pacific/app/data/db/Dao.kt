package com.pacific.app.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao : AppDao<DbUser> {

    @Query("SELECT * FROM user WHERE _id =:id")
    fun get(id: Long): DbUser?
}