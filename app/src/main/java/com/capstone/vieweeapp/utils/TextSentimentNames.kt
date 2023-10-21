package com.capstone.vieweeapp.utils

object TextSentimentNames {

    val en = listOf(
        "positive",
        "neutral",
        "negative"
    )

    val ko = listOf(
        "긍정",
        "보통",
        "부정",
    )

    fun translationToKorean(name: String): String {
        return when(name) {
            en[0] -> ko[0]
            en[1] -> ko[1]
            en[2] -> ko[2]
            else -> ko[1]
        }
    }
}