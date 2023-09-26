package com.capstone.vieweeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.presentation.event.FeedbackForHomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackForHomeViewModel @Inject constructor(
    private val interviewResultRepository: InterviewResultRepository
): ViewModel() {

    fun uiEvent(uiEvent: FeedbackForHomeUiEvent) {
        when(uiEvent) {
            is FeedbackForHomeUiEvent.DeleteFeedback -> {
                viewModelScope.launch {
                    interviewResultRepository.deleteInterviewResult(uiEvent.interviewResult)
                }
            }
        }
    }
}