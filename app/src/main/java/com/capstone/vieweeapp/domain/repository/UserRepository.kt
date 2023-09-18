package com.capstone.vieweeapp.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.vieweeapp.data.source.local.entity.User

interface UserRepository {

    suspend fun getUser(): User

    suspend fun insertResume(user: User)

    suspend fun deleteUser(user: User)

    suspend fun deleteAllUser()
}