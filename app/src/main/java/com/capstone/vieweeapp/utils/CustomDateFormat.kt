package com.capstone.vieweeapp.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object CalculateDate {

    fun today(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> ""
        }

        val dateFormat = "yyyy.MM.dd"
        val date = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat(dateFormat)

        val simpleDate: String = simpleDateFormat.format(date)

        return "$simpleDate $dayOfWeek"
    }
}