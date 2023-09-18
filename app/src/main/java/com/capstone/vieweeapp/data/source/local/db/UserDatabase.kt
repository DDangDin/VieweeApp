package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.vieweeapp.data.source.local.entity.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}