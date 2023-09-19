package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

data class InterviewResultsState(
    val interviewResults: List<InterviewResult> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
