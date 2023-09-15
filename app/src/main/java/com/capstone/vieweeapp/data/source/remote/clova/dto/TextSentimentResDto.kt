package com.capstone.viewee.data.source.network.clova_api.dto

import com.capstone.vieweeapp.data.source.local.entity.TextSentiment

data class TextSentimentResDto(
    val document: Document,
    val sentences: List<Sentence>
)

fun TextSentimentResDto.toTextSentiment(): TextSentiment {
    return TextSentiment(
        sentiment = document.sentiment,
        confidence = document.confidence
    )
}