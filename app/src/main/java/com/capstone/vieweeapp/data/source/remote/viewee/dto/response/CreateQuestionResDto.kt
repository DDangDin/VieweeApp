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
        // version 1
//        questions
//            .split("\n")
//            .filter { it.isNotEmpty() }
//            .map { it.split(":")[1] }

        // version 2
//        questions
//            .split("\n")
//            .filter { it.isNotEmpty() }
//            .map {
//                if (it.split(":").size >= 2) {
//                    it.split(":")[1]
//                } else {
//                    it
//                }
//            }
//            .filterIndexed { index, string ->
//                index % 2 != 0
//            }
//            .map { it.replace("-", "") }

        // version 3
        questions
            .split("\n", ":", "-")
            .filter { it.isNotEmpty() }
            .filter { it.length >= 11 }
            .map { it.replace("-", "") }
    } else {
        listOf(questions)
    }
}

