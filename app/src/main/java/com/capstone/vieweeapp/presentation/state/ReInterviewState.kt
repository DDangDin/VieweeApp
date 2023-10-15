package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto

data class ReInterviewState(
    val reFeedback: List<FeedbackResDto>? = null,
    val loading: Boolean = false,
    val error: String = ""
)