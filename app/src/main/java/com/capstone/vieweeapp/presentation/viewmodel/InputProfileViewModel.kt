package com.capstone.vieweeapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.data.source.local.entity.ResumeDetail
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.presentation.event.SelectResumeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputProfileViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository
): ViewModel() {

    var inputName = mutableStateOf("")
        private set
    var inputBirthdate = mutableStateOf("")
        private set
    var inputEducation = mutableStateOf("")
        private set
    var inputCareer = mutableStateOf("")
        private set
    var inputSupportJobs = mutableStateOf("")
        private set
    var inputCertifications = mutableStateOf("")
        private set
    var inputSkills = mutableStateOf("")
        private set
    var inputResumeText = mutableStateOf("")
        private set

    fun inputNameChanged(value: String) {
        inputName.value = value
    }
    fun inputBirthdateChanged(value: String) {
        inputBirthdate.value = value
    }
    fun inputEducationChanged(value: String) {
        inputEducation.value = value
    }
    fun inputCareerChanged(value: String) {
        inputCareer.value = value
    }
    fun inputSupportJobsChanged(value: String) {
        inputSupportJobs.value = value
    }
    fun inputCertificationsChanged(value: String) {
        inputCertifications.value = value
    }
    fun inputSkillsChanged(value: String) {
        inputSkills.value = value
    }
    fun inputResumeChanged(value: String) {
        inputResumeText.value = value
    }

    fun initializeInputData() {
        inputName.value = ""
        inputBirthdate.value = ""
        inputEducation.value = ""
        inputCareer.value = ""
        inputSupportJobs.value = ""
        inputCertifications.value = ""
        inputSkills.value = ""
        inputResumeText.value = ""
    }

    fun insertResume() {
        viewModelScope.launch {

            val resume = Resume(
                name = inputName.value,
                birth = inputBirthdate.value,
                education = inputEducation.value,
                career = inputCareer.value,
                etc = "",
                resumeDetail = ResumeDetail(
                    supportJob = inputSupportJobs.value,
                    certificate = inputCertifications.value.split(","),
                    skill = inputSkills.value.split(","),
                    resumeText = inputResumeText.value,
                )
            )

            resumeRepository.insertResume(resume)
        }
    }
}