package com.pacific.app.architecture.store

import com.pacific.app.architecture.store.db.PlatformDatabase
import com.pacific.app.architecture.store.db.UserDao

object TestDatabase : PlatformDatabase {

    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }
}