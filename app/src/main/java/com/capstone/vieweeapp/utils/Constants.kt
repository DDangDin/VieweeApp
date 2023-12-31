package com.capstone.vieweeapp.utils

import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.data.source.local.entity.ResumeDetail

object Constants {

    const val VOICE_TO_TEXT_LANGUAGE = "ko-KR"

    // API Endpoint
    const val CLOVA_SENTIMENT_BASE_URL = "https://naveropenapi.apigw.ntruss.com"
    const val VIEWEE_MOCK_SERVER_BASE_URL = "https://4dfc4c1a-ecd0-4d8a-9979-aeeb3a874d29.mock.pstmn.io"
    const val VIEWEE_REAL_SERVER_BASE_URL = "http://ec2-52-79-155-234.ap-northeast-2.compute.amazonaws.com:8080"

    // Toast Message - RealInterviewScreen
    const val SPOKEN_ANSWER_TEXT_EMPTY = "답변 후 다음 질문으로 넘어갈 수 있습니다."

    // Messages
    const val UNKNOWN_NAME = "Unknown"
    const val COMMON_ERROR_MESSAGE = "알 수 없는 오류"
    const val INTERVIEW_FINISH_MESSAGE = " 수고하셨습니다. 면접이 종료 되었습니다. 하단에 완료 버튼을 눌러, 결과를 기다려 주시기 바랍니다."

    // Separator
    const val RESUME_SEPARATOR = ","
    const val FEEDBACK_SEPARATOR = "\n"

    // PaddingValues
    const val HOME_PADDING_VALUE_HORIZONTAL = 30
    const val HOME_PADDING_VALUE_VERTICAL = 40
    const val BOTTOM_NAV_BAR_PADDING = 56

    // InputProfileScreen - ResumeText 글자 수 제한
    const val RESUME_TEXT_MAX_LENGTH = 600

    // PutExtra Name
    const val PUT_EXTRA_PREVIOUS_INTERVIEW_RESULT_INDEX = "previousInterviewResultIndex"
    const val PUT_EXTRA_IS_RE_INTERVIEW = "isReInterview"


    // DummyData
    val INTERVIEW_RESULT_EMPTY_DATA = InterviewResult(
        id = 0,
        feedbacks = Feedbacks(emptyList()),
        questions = emptyList(),
        answers = emptyList(),
        emotions = emptyList(),
        textSentiments = emptyList(),
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

    // SharedPreference
    const val USER_SHARED_PREFERENCE = "user"

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