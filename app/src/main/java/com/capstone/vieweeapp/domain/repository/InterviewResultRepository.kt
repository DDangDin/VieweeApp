package com.capstone.vieweeapp.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

interface InterviewResultRepository {

    suspend fun getInterviewResults(): List<InterviewResult>

    suspend fun insertInterviewResult(interviewResult: InterviewResult)

    suspend fun deleteInterviewResult(interviewResult: InterviewResult)

    suspend fun deleteAllInterviewResult()


    // 재면접
    suspend fun updateForReInterviewResult(interviewResult: InterviewResult)

    suspend fun getInterviewResultOnce(id: Int): InterviewResult
}