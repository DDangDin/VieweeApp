package com.capstone.vieweeapp.utils

import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.data.source.local.entity.ResumeDetail

object Constants {

    // API Endpoint
    const val CLOVA_SENTIMENT_BASE_URL = "https://naveropenapi.apigw.ntruss.com"
    const val VIEWEE_MOCK_SERVER_BASE_URL = "https://4dfc4c1a-ecd0-4d8a-9979-aeeb3a874d29.mock.pstmn.io"

    // Messages
    const val UNKNOWN_NAME = "Unknown"
    const val COMMON_ERROR_MESSAGE = "알 수 없는 오류"

    // Separator
    const val RESUME_SEPARATOR = ","
    const val FEEDBACK_SEPARATOR = "\n"

    // PaddingValues
    const val HOME_PADDING_VALUE_HORIZONTAL = 30
    const val HOME_PADDING_VALUE_VERTICAL = 40
    const val BOTTOM_NAV_BAR_PADDING = 56

    // InputProfileScreen - ResumeText 글자 수 제한
    const val RESUME_TEXT_MAX_LENGTH = 600

    // DummyData
    val INTERVIEW_RESULT_EMPTY_DATA = InterviewResult(
        id = 0,
        feedbacks = Feedbacks(emptyList()),
        questions = emptyList(),
        answers = emptyList(),
        emotions = emptyList(),
        textSentiment = emptyList(),
        feedbackTotal = "",
        date = "2023.09.18"
    )

    val RESUME_DUMMY_DATA = Resume(
        id = 0,
        birth = "",
        education = "",
        career = "",
        etc = "",
        name = "자기소개서",
        resumeDetail = ResumeDetail(
            supportJob = "",
            certificate = emptyList(),
            skill = emptyList(),
            resumeText = "자기소개서 텍스트 테스트 자기소개서 텍스트 테스트 자기소개서 텍스트 테스트 자기소개서 " +
                    "텍스트 테스트 자기소개서 텍스트 테스트" +
                    " 자기소개서 텍스트 테스트 자기소개서 텍스트 테스트 자기소개서 텍스트 테스트 " +
                    "자기소개서 텍스트 테스트 자기소개서 텍스트 테스트"
        )
    )

    fun getInterviewResultList(): List<InterviewResult> {
        val datas = arrayListOf<InterviewResult>()
        repeat(10) {
            datas.add(INTERVIEW_RESULT_EMPTY_DATA)
        }
        return datas
    }

    fun getResumeList(): List<Resume> {
        val datas = arrayListOf<Resume>()
        repeat(10) {
            datas.add(RESUME_DUMMY_DATA)
        }
        return datas
    }
}