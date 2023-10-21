package com.capstone.vieweeapp.data.source.local.entity

import android.util.Log
import com.capstone.vieweeapp.utils.FacialEmotionNames

data class Emotion(
    val surprise: Int,
    val fear: Int,
    val angry: Int,
    val neutral: Int,
    val sad: Int,
    val disgust: Int,
    val happy: Int
)

fun List<Emotion>.toPercentages(): List<Pair<String, Float>> {
    /** TODO 재면접 시 2차 것만 할지 전부 다 할지 생각 **/
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
        Pair(FacialEmotionNames.en[0], (surprise/total*100)),
        Pair(FacialEmotionNames.en[1], (fear/total*100)),
        Pair(FacialEmotionNames.en[2], (angry/total*100)),
        Pair(FacialEmotionNames.en[3], (neutral/total*100)),
        Pair(FacialEmotionNames.en[4], (sad/total*100)),
        Pair(FacialEmotionNames.en[5], (disgust/total*100)),
        Pair(FacialEmotionNames.en[6], (happy/total*100)),
    )
}

fun Emotion.toPairList(): List<Pair<String, Int>> {
    return listOf(
        Pair(FacialEmotionNames.en[0], surprise),
        Pair(FacialEmotionNames.en[1], fear),
        Pair(FacialEmotionNames.en[2], angry),
        Pair(FacialEmotionNames.en[3], neutral),
        Pair(FacialEmotionNames.en[4], sad),
        Pair(FacialEmotionNames.en[5], disgust),
        Pair(FacialEmotionNames.en[6], happy),
    )
}
