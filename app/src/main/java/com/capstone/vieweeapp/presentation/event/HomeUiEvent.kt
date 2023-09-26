package com.capstone.vieweeapp.presentation.event

import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

sealed class HomeUiEvent {
    data class OpenInterviewResult(val index: Int): HomeUiEvent() // 미사용
    data class DeleteInterviewResult(val interviewResult: InterviewResult): HomeUiEvent()
}
