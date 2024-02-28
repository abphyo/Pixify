package com.biho.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.biho.database.daos.UserDao
import com.biho.database.entity.User

@Database(
    entities = [User::class],
    version = 1
)
@TypeConverters(UserTypeConvertors::class)
abstract class PixifyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}