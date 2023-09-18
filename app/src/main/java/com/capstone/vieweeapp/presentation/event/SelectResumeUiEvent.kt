package com.capstone.vieweeapp.presentation.event

import com.capstone.vieweeapp.data.source.local.entity.Resume
import com.capstone.vieweeapp.navigation.Screen

sealed class SelectResumeUiEvent {
    data class SelectedResume(val resume: Resume): SelectResumeUiEvent()
    data class DeleteResume(val resume: Resume): SelectResumeUiEvent()
}
