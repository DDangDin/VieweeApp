package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.data.source.local.db.InterviewResultDao
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository

class InterviewResultRepositoryImpl(
    private val dao: InterviewResultDao
): InterviewResultRepository {

    override suspend fun getInterviewResults(): List<InterviewResult> {
        return dao.getInterviewResults()
    }

    override suspend fun insertInterviewResult(interviewResult: InterviewResult) {
        dao.insertInterviewResult(interviewResult)
    }

    override suspend fun deleteInterviewResult(interviewResult: InterviewResult) {
        dao.deleteInterviewResult(interviewResult)
    }

    override suspend fun deleteAllInterviewResult() {
        dao.deleteAllInterviewResult()
    }
}