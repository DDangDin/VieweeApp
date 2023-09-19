package com.capstone.vieweeapp.data.source.remote.viewee.dto.response

data class CreateQuestionResDto(
    val questions: String
)

fun CreateQuestionResDto.makeQuestionList(): List<String> {
    // 가공 필요
    // 1. GPT 한테 받아온 질문 \n 단위로 끊어 재생
    // 2. 앞에 '#1:' 부분 자름
    // issue:
    // 1. 가끔 '세요.' 라는 텍스트가 있음
    return if (questions.contains("\n")) {
        questions
            .split("\n")
            .filter { it.isNotEmpty() }
            .map { it.substring(3) }
    } else {
        listOf(questions)
    }
}