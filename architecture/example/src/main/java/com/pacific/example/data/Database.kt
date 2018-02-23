package com.pacific.example.data

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Entity(tableName = "app_info")
data class AppInfo(
        @ColumnInfo(name = "login_name") val loginName: String,
        @ColumnInfo(name = "login_password") val loginPassword: String,
        @ColumnInfo(name = "downloaded_version") val downloadedVersion: String,
        @ColumnInfo(name = "open_pattern") val openPattern: Boolean,
        @ColumnInfo(name = "show_pattern_path") val showPatternPath: Boolean,
        @ColumnInfo(name = "pattern") val pattern: Boolean,
        @ColumnInfo(name = "error_pattern_count") val errorPatternCount: Int,
        @ColumnInfo(name = "error_password_count") val errorPasswordCount: Int,
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int? = null
)

@Entity(tableName = "bank")
data class Bank(
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "bin") val bin: String,
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id: Int? = null
)

@Dao
interface AppInfoDao {
    @Insert(onConflict = REPLACE)
    fun insert(appInfo: AppInfo)

    @Insert(onConflict = REPLACE)
    fun update(bank: Bank)
}

@Dao
interface BankDao {
    @Insert(onConflict = REPLACE)
    fun insert(bank: Bank)

    @Insert(onConflict = REPLACE)
    fun update(bank: Bank)
}

@Database(entities = [(AppInfo::class), (Bank::class)], version = 1)
abstract class SystemDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    abstract fun bankDao(): BankDao
}