package com.pacific.data.db

interface AppDatabase {

    fun userDao(): UserDao
}