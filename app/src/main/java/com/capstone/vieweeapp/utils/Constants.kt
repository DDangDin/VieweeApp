package com.capstone.vieweeapp.utils

import androidx.compose.ui.unit.dp
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

object Constants {

    // Text
    const val UNKNOWN_NAME = "Unknown"

    // Separator
    const val RESUME_SEPARATOR = ","
    const val FEEDBACK_SEPARATOR = "\n"

    // PaddingValues
    const val HOME_PADDING_VALUE_HORIZONTAL = 30
    const val HOME_PADDING_VALUE_VERTICAL = 40
    const val BOTTOM_NAV_BAR_PADDING = 56

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

    fun getInterviewResultList(): List<InterviewResult> {
        val datas = arrayListOf<InterviewResult>()
        repeat(10) {
            datas.add(INTERVIEW_RESULT_EMPTY_DATA)
        }
        return datas
    }
}