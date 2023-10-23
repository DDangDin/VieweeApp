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

fun List<TextSentiment>.average(isReInterview: Boolean): List<Float> {
    /** TODO 재면접 시 2차 것만 할지 전부 다 할지 생각(일단은 전부 다 계산 함) **/
    val positive = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.positive)}.toFloat()
    val neutral = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.neutral)}.toFloat()
    val negative = this.fold(0.0) { acc, sentiment -> acc + (sentiment.confidence.negative)}.toFloat()

    return if (isReInterview) {
        listOf(positive/10, neutral/10, negative/10)
    } else {
        listOf(positive/5, neutral/5, negative/5)
    }
}

fun TextSentiment.toFloatList(): List<Float> {
    return listOf(
        this.confidence.positive.toFloat(),
        this.confidence.neutral.toFloat(),
        this.confidence.negative.toFloat(),
    )
}