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

//fun Emotion.toFeedbackReq(): String {
//
//}