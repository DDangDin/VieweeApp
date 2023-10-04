package com.capstone.vieweeapp.data.source.local.entity

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

fun Emotion.toPercentages(): List<Float> {
    val total = surprise + fear + angry + neutral
    + sad + disgust + happy

    return listOf(
        (surprise/total*100).toFloat(),
        (fear/total*100).toFloat(),
        (angry/total*100).toFloat(),
        (neutral/total*100).toFloat(),
        (sad/total*100).toFloat(),
        (disgust/total*100).toFloat(),
        (happy/total*100).toFloat(),
    )
}