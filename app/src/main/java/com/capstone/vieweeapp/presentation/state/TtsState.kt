package com.capstone.vieweeapp.presentation.state

data class TtsState(
    val isSpeak: Boolean = true, // 위험한 방법, 나중에 바꿔야함
    val error: String = ""
)