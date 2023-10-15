package com.capstone.vieweeapp.data.source.local.entity

import android.util.Log
import java.util.Collections.max

data class Emotion(
    val surprise: Int,
    val fear: Int,
    val angry: Int,
    val neutral: Int,
    val sad: Int,
    val disgust: Int,
    val happy: Int
)

fun List<Emotion>.toPercentages(): List<Float> {
    val surprise = this.fold(0) { acc, emotion -> acc + emotion.surprise }.toFloat()
    val fear = this.fold(0) { acc, emotion -> acc + emotion.fear }.toFloat()
    val angry = this.fold(0) { acc, emotion -> acc + emotion.angry }.toFloat()
    val neutral = this.fold(0) { acc, emotion -> acc + emotion.neutral }.toFloat()
    val sad = this.fold(0) { acc, emotion -> acc + emotion.sad }.toFloat()
    val disgust = this.fold(0) { acc, emotion -> acc + emotion.disgust }.toFloat()
    val happy = this.fold(0) { acc, emotion -> acc + emotion.happy }.toFloat()

    val total = surprise + fear + angry + neutral + sad + disgust + happy

    Log.d("emotionToPercentages", "total: $total, surprise: $neutral, calc: ${(neutral/total*100.0)}")

    return listOf(
        (surprise/total*100),
        (fear/total*100),
        (angry/total*100),
        (neutral/total*100),
        (sad/total*100),
        (disgust/total*100),
        (happy/total*100),
    )
}