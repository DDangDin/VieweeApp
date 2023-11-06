package com.capstone.vieweeapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository
): ViewModel() {

    private val _resumes = MutableStateFlow(listOf<Resume>())
    val resumes = _resumes.asStateFlow()

    fun getResumes() {
        viewModelScope.launch {
            _resumes.update { resumeRepository.getResumes() }
        }
    }
}