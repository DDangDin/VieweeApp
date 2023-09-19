package com.capstone.vieweeapp.presentation.event

import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment

sealed class RealInterviewUiEvent {
    object StartInterview: RealInterviewUiEvent()
    object FinishInterview: RealInterviewUiEvent()
    object ChangeInterviewerTurn: RealInterviewUiEvent()
    data class NextQuestion(
        val answer: String,
    ): RealInterviewUiEvent()

    object StartFeedback: RealInterviewUiEvent()
}
