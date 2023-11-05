package com.capstone.vieweeapp.utils

//typealias minute = Int

object CalculateTime {

    fun Int.timeWithMinute(): Pair<Int, Int> {
        if (this > 59) {
            val minute = this / 60
            val second = this % 60
            return minute to second
        } else {
            return 0 to this
        }
    }
}