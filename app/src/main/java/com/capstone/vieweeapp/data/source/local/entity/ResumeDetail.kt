package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.PrimaryKey

data class ResumeDetail(
    val certificate: List<String>,  // 자격증/어학/수상내역 (여러 개)
    val supportJob: String,         // 지원직무
    val skill: List<String>,        // 보유기술/능력 (여러 개)
    val resumeText: String          // 자기소개서
)
