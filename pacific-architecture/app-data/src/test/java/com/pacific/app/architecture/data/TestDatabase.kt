package com.pacific.app.architecture.data

import com.pacific.app.architecture.data.db.AppDatabase
import com.pacific.app.architecture.data.db.UserDao

object TestDatabase : AppDatabase {

    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }
}