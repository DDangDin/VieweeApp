package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

@Dao
interface InterviewResultDao {

    @Query("SELECT * FROM interview_result_db")
    suspend fun getInterviewResults(): List<InterviewResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterviewResult(interviewResult: InterviewResult)

    @Delete
    suspend fun deleteInterviewResult(interviewResult: InterviewResult)

    @Query("DELETE FROM interview_result_db")
    suspend fun deleteAllInterviewResult()

    // 재면접
    @Upsert
    suspend fun updateForReInterviewResult(interviewResult: InterviewResult)

    @Query("SELECT * FROM interview_result_db WHERE id = :id")
    suspend fun getInterviewResultOnce(id: Int): InterviewResult

    @Query("SELECT date FROM interview_result_db")
    suspend fun getDateList(): List<String>
}