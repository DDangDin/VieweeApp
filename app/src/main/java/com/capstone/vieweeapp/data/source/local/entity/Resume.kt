package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.utils.Constants

@Entity("resume_db")
data class Resume(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,       // resume name
    val birth: String,
    val education: String,  // 학력
    val career: String,     // 경력
    val resumeDetail: ResumeDetail,
    val etc: String = "",
)

fun Resume.toCreateQuestionReqDto(): CreateQuestionReqDto {
    return CreateQuestionReqDto(
        name = name,
        birth = birth,
        career = career,
        education = education,
        certificate = resumeDetail.certificate.joinToString(Constants.RESUME_SEPARATOR),
        supportJob = resumeDetail.supportJob,
        resume = resumeDetail.resumeText
    )
}
