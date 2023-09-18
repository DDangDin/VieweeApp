package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.vieweeapp.data.source.local.entity.Resume

@Dao
interface ResumeDao {

    @Query("SELECT * FROM resume_db")
    suspend fun getResumes(): List<Resume>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResume(resume: Resume)

    @Delete
    suspend fun deleteResume(resume: Resume)

    @Query("DELETE FROM resume_db")
    suspend fun deleteAllResume()
}