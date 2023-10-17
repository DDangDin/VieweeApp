package com.capstone.vieweeapp.presentation.event

sealed class RealInterviewUiEvent {
    object StartInterview: RealInterviewUiEvent()
    object FinishInterview: RealInterviewUiEvent()
    object ChangeInterviewerTurn: RealInterviewUiEvent()
    data class NextQuestion(
        val answer: String,
    ): RealInterviewUiEvent()

    data class StartFeedback(val isReInterview: Boolean, val previousInterviewResultId: Int): RealInterviewUiEvent()
}
