package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto

data class QuestionsState(
    val questions: List<String> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
