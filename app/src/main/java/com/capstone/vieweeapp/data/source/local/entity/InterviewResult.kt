package com.capstone.vieweeapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("interview_result_db")
data class InterviewResult(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val questions: List<String>,
    val answers: List<String>,
    val textSentiment: List<TextSentiment>,
    val emotions: List<Emotion>,
    val feedbacks: Feedbacks,
    val feedbackTotal: String,
    val date: String
)
