package com.capstone.vieweeapp.utils

object FacialEmotionNames {

    val en = listOf(
        "Surprise",
        "Fear",
        "Angry",
        "Neutral",
        "Sad",
        "Disgust",
        "Happy",
    )

    val ko = listOf(
        "놀람",
        "두려움",
        "화남",
        "보통",
        "슬픔",
        "혐오",
        "행복"
    )

    fun translationToKorean(name: String): String {
        return when(name) {
            en[0] -> ko[0]
            en[1] -> ko[1]
            en[2] -> ko[2]
            en[3] -> ko[3]
            en[4] -> ko[4]
            en[5] -> ko[5]
            en[6] -> ko[6]
            else -> ko[3]
        }
    }
}