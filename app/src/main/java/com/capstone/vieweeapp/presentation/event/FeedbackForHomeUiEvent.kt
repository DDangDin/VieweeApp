package com.capstone.vieweeapp.presentation.event

import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

sealed class FeedbackForHomeUiEvent {
    data class DeleteFeedback(val interviewResult: InterviewResult): FeedbackForHomeUiEvent()
}
