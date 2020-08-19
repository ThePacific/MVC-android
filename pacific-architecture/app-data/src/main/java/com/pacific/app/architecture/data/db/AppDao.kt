package com.pacific.app.architecture.data.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 *  1. SELECT * FROM table LIMIT offset_count , row_count
 *  2. SELECT * FROM table LIMIT row_count OFFSET offset_count
 *  3. SELECT * FROM table LIMIT row_count
 *  4. SELECT * FROM table ORDER BY column ASC/DESC
 */
interface AppDao<E> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: E): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: E): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<E>): List<Long>

    @Delete
    fun delete(entity: E): Int

    @Delete
    fun deleteAll(vararg entity: E): Int

    @Delete
    fun deleteAll(entities: List<E>): Int

    @Update
    fun update(entity: E): Int

    @Update
    fun updateAll(vararg entity: E): Int

    @Update
    fun updateAll(entities: List<E>): Int
}