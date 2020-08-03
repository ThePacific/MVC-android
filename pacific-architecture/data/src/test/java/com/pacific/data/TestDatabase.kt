package com.pacific.data

import com.pacific.data.db.AppDatabase
import com.pacific.data.db.UserDao

object TestDatabase : AppDatabase {

    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }
}