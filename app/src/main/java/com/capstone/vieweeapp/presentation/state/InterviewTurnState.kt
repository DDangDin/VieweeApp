package com.capstone.vieweeapp.presentation.state

data class InterviewTurnState(
    val isInterviewerTurn: Boolean = true,
    val turnIndex: Int = 0,
    val loading: Boolean = false,
    val error: String = ""
)
