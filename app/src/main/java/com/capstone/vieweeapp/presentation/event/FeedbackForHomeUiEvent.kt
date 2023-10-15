package com.capstone.vieweeapp.presentation.event

import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto

sealed class FeedbackForHomeUiEvent {
    data class DeleteFeedback(val interviewResult: InterviewResult) : FeedbackForHomeUiEvent()
    data class EachReInterview(
        val index: Int,
        val maxIndex: Int,
        val answer: String
    ) : FeedbackForHomeUiEvent()
}