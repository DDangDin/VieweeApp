package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto

data class ReInterviewState(
    val reFeedbacks: List<FeedbackResDto> = emptyList(),
    val loadings: Boolean = false,
    val errors: String = "",
)