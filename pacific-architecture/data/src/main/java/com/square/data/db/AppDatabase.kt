package com.square.data.db

interface AppDatabase {

    fun userDao(): UserDao
}