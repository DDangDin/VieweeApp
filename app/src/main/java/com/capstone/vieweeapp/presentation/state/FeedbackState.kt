package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult
import com.capstone.vieweeapp.data.source.local.entity.Resume

data class FeedbackState(
    val previousInterviewResult: InterviewResult? = null,
    val feedbacks: Feedbacks = Feedbacks(emptyList()),
    val resumeForFeedback: Resume? = null,
    val loading: Boolean = false,
    val error: String = ""
)
