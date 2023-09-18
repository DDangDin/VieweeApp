package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.data.source.local.db.ResumeDao
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.domain.repository.ResumeRepository

class ResumeRepositoryImpl(
    private val dao: ResumeDao
): ResumeRepository {

    override suspend fun getResumes(): List<Resume> {
        return dao.getResumes()
    }

    override suspend fun insertResume(resume: Resume) {
        dao.insertResume(resume)
    }

    override suspend fun deleteResume(resume: Resume) {
        dao.deleteResume(resume)
    }

    override suspend fun deleteAllResume() {
        dao.deleteAllResume()
    }
}