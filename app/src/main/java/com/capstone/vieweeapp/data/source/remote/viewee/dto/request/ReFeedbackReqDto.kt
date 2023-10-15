package com.capstone.vieweeapp.data.source.remote.viewee.dto.request

import com.google.gson.annotations.SerializedName

data class ReFeedbackReqDto(
    val textSentimentAnalysisData: String,
    val facialExpressionAnalysisData: String,
    @SerializedName("answerFeed")
    val answerFeedback: String,
    @SerializedName("overrallFeed")
    val allAnswersFeedback: String,
    val reAnswer: String
)
