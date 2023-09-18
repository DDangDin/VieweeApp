package com.capstone.vieweeapp.presentation.state

import com.capstone.vieweeapp.data.source.local.entity.Resume

data class ResumeState(
    val resumes: List<Resume> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
