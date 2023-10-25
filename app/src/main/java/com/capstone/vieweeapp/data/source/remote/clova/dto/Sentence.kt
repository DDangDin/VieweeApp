package com.capstone.vieweeapp.data.source.remote.clova.dto

data class Sentence(
    val confidence: Confidence,
    val content: String,
    val highlights: List<Highlight>,
    val length: Int,
    val offset: Int,
    val sentiment: String
)