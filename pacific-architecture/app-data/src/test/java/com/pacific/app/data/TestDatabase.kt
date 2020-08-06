package com.pacific.app.data

import com.pacific.app.data.db.AppDatabase
import com.pacific.app.data.db.UserDao

object TestDatabase : AppDatabase {

    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }
}