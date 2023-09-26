package com.capstone.vieweeapp.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.presentation.event.HomeUiEvent
import com.capstone.vieweeapp.presentation.state.InterviewResultsState
import com.capstone.vieweeapp.presentation.view.interview.input_profile.CustomTitleText
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.CustomSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interviewResultRepository: InterviewResultRepository
): ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _interviewResultsState = MutableStateFlow(InterviewResultsState())
    val interviewResultsState = _interviewResultsState.asStateFlow()

    fun onSearchTextChanged(value: String) {
        _searchText.value = value
    }

    fun getInterviewResults() {
        viewModelScope.launch {
            _interviewResultsState.update { it.copy(loading = true) }
            val interviewResults = interviewResultRepository.getInterviewResults()
            _interviewResultsState.update { it.copy(
                interviewResults = interviewResults,
                loading = false
            ) }
        }
    }

    fun updateUsername(username: String) {
        _username.value = username
    }
}