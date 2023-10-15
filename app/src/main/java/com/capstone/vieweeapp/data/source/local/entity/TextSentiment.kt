package com.capstone.vieweeapp.data.source.local.entity

import android.util.Log
import com.capstone.viewee.data.source.network.clova_api.dto.Confidence

data class TextSentiment(
    val sentiment: String,
    val confidence: Confidence,
)

fun TextSentiment.toFeedbackReq(): String {
    return sentiment
}

fun List<TextSentiment>.toPercentage(): List<Float> {
    val positive = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.positive)}.toFloat()
    val neutral = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.neutral)}.toFloat()
    val negative = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.negative)}.toFloat()
    Log.d("textSentimentToPercentage", "${positive/5}, ${neutral/5}, ${negative/5}")
    return listOf(positive/5, neutral/5, negative/5)
}
