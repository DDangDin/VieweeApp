package com.capstone.vieweeapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.data.source.local.entity.toCreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.makeQuestionList
import com.capstone.vieweeapp.domain.repository.ClovaSentimentRepository
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.presentation.event.SelectResumeUiEvent
import com.capstone.vieweeapp.presentation.state.QuestionsState
import com.capstone.vieweeapp.presentation.state.ResumeState
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository,
    private val interviewResultRepository: InterviewResultRepository,
    private val vieweeRepository: VieweeRepository,
    private val clovaSentimentRepository: ClovaSentimentRepository
): ViewModel() {
    private val TAG = "InterviewViewModel_Log"

    // resume 선택 시 아래 변수에 할당 됨
    private var selectedResume = Constants.RESUME_DUMMY_DATA

    private val _resumeState = MutableStateFlow(ResumeState())
    val resumeState = _resumeState.asStateFlow()

    private val _questionsState = MutableStateFlow(QuestionsState())
    val questionsState = _questionsState.asStateFlow()

    private val _shouldShowCamera = mutableStateOf(false)
    val shouldShowCamera: State<Boolean> = _shouldShowCamera

    private val _shouldRecordAudio = mutableStateOf(false)
    val shouldRecordAudio: State<Boolean> = _shouldRecordAudio

    private val _hasPermissionsForInterview = mutableStateOf(false)
    val hasPermissionsForInterview: State<Boolean> = _hasPermissionsForInterview


    fun getResumes() {
        viewModelScope.launch {
            _resumeState.update { it.copy(loading = true) }
            val resumes = resumeRepository.getResumes()
            _resumeState.update { it.copy(
                resumes = resumes,
                loading = false
            ) }
        }
    }

    fun selectResumeUiEvent(uiEvent: SelectResumeUiEvent) {
        when(uiEvent) {
            is SelectResumeUiEvent.SelectedResume -> {
                selectedResume = uiEvent.resume
            }
            is SelectResumeUiEvent.DeleteResume -> {
                viewModelScope.launch {
                    resumeRepository.deleteResume(uiEvent.resume)
                    getResumes()
                }
            }
        }
    }

    // Permissions Logic
    fun updatePermissions(permissionName: String, valid: Boolean) {
        when (permissionName) {
            android.Manifest.permission.CAMERA -> _shouldShowCamera.value = valid
            android.Manifest.permission.RECORD_AUDIO -> _shouldRecordAudio.value = valid
        }

        if (_shouldShowCamera.value && _shouldRecordAudio.value)
            _hasPermissionsForInterview.value = true
    }

    fun updateFacialExpression(emotion: String, emotionValue: Float) {
        // facialExpressionRecognition
        // emotion -> emotion_s
        // emotion_value -> emotion_v
        Log.d(TAG, emotion)
        viewModelScope.launch {
//            if (isFacialExpressionUpdatePossible.value) {
//                // _facialExpressionHashMap 초기화가 된 상태에서 진행되어야 함
//                // 초기화 할 때 까지 기다림
//                val count = _facialExpressionHashMap[emotion]
//                _facialExpressionHashMap[emotion] = count!! + 1
//            }
        }
    }

    fun createQuestions() {
        viewModelScope.launch {
            vieweeRepository.getQuestions(selectedResume.toCreateQuestionReqDto()).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _questionsState.update { it.copy(
                            questions = result.data?.makeQuestionList() ?: emptyList(),
                            loading = false
                        ) }
                    }
                    is Resource.Loading -> {
                        _questionsState.update { it.copy(loading = true) }
                    }
                    is Resource.Error -> {
                        _questionsState.update { it.copy(
                            error = result.message ?: Constants.COMMON_ERROR_MESSAGE,
                            loading = false
                        ) }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}