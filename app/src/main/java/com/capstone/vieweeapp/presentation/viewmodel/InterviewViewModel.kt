package com.capstone.vieweeapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.presentation.state.ResumeState
import com.capstone.vieweeapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository,
    private val interviewResultRepository: InterviewResultRepository
): ViewModel() {

    private val _resumeState = MutableStateFlow(ResumeState())
    val resumeState = _resumeState.asStateFlow()

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
}