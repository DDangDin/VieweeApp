package com.capstone.vieweeapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.ReFeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.presentation.event.FeedbackForHomeUiEvent
import com.capstone.vieweeapp.presentation.state.ReInterviewState
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackForHomeViewModel @Inject constructor(
    private val interviewResultRepository: InterviewResultRepository,
    private val vieweeRepository: VieweeRepository
) : ViewModel() {

    // 각 재면접 상태 값
    private val _reInterviewState = MutableStateFlow(ReInterviewState())
    val reInterviewState: StateFlow<ReInterviewState> = _reInterviewState.asStateFlow()

    private val tempReFeedbacks = arrayListOf<FeedbackResDto>()

    fun uiEvent(uiEvent: FeedbackForHomeUiEvent) {
        when (uiEvent) {
            is FeedbackForHomeUiEvent.DeleteFeedback -> {
                viewModelScope.launch {
                    interviewResultRepository.deleteInterviewResult(uiEvent.interviewResult)
                }
            }

            is FeedbackForHomeUiEvent.EachReInterview -> {
                viewModelScope.launch {
                    vieweeRepository.getReAnswerFeedback1(
                        ReFeedbackReqDto(
                            facialExpressionAnalysisData = "",
                            textSentimentAnalysisData = "",
                            allAnswersFeedback = "",
                            answerFeedback = "",
                            reAnswer = uiEvent.answer
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                Log.d("reInterview_Answer_Log", "수정된 답변: ${uiEvent.answer}")
                                if (tempReFeedbacks.isEmpty()) {
                                    repeat(uiEvent.maxIndex) {
                                        tempReFeedbacks.add(FeedbackResDto(""))
                                    }
                                }
                                tempReFeedbacks[uiEvent.index] = result.data ?: FeedbackResDto(Constants.COMMON_ERROR_MESSAGE)
                                Log.d("reInterview_Answer_Log", "상태 값: $tempReFeedbacks")
                                _reInterviewState.update {
                                    it.copy(
                                        reFeedback = tempReFeedbacks,
                                        loading = false
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _reInterviewState.update {
                                    it.copy(
                                        loading = true
                                    )
                                }
                            }

                            is Resource.Error -> TODO()
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}