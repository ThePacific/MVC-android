package com.pacific.app.architecture.store.db

import androidx.room.Dao
import androidx.room.Query
import com.pacific.guava.data.SqlDao

@Dao
interface UserDao : SqlDao<DbUser> {

    @Query("SELECT * FROM user WHERE _id =:id")
    fun get(id: Long): DbUser?
}