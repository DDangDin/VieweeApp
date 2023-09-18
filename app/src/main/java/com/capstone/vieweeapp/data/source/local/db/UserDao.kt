package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.vieweeapp.data.source.local.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user_db")
    suspend fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResume(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_db")
    suspend fun deleteAllUser()
}