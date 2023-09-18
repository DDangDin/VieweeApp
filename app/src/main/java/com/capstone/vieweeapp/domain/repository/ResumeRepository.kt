package com.capstone.vieweeapp.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.vieweeapp.data.source.local.entity.Resume

interface ResumeRepository {

    suspend fun getResumes(): List<Resume>

    suspend fun insertResume(resume: Resume)

    suspend fun deleteResume(resume: Resume)

    suspend fun deleteAllResume()
}