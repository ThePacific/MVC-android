package com.pacific.app.architecture.store.db

interface PlatformDatabase {

    fun userDao(): UserDao
}