package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("interview_result_db")
data class InterviewResult(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val questions: List<String>,
    val answers: List<String>,
    val textSentiments: List<TextSentiment>,
    val emotions: List<Emotion>,
    val feedbacks: Feedbacks,
    val feedbackTotal: String,
    val date: String,
    val etc: String = "", // 재면접 표시
)

