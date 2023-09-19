package com.capstone.vieweeapp.presentation.state

data class VoiceToTextParserState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error: String? = ""
)