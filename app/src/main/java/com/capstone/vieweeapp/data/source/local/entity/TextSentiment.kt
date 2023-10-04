package com.capstone.vieweeapp.data.source.local.entity

import com.capstone.viewee.data.source.network.clova_api.dto.Confidence

data class TextSentiment(
    val sentiment: String,
    val confidence: Confidence,
)

fun TextSentiment.toFeedbackReq(): String {
    return sentiment
}

fun TextSentiment.toPercentage(): List<Float> {
    return listOf(
        confidence.neutral.toFloat(),
        confidence.positive.toFloat(),
        confidence.negative.toFloat()
    )
}
