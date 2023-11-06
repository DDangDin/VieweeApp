package com.capstone.vieweeapp.data.source.remote.viewee.dto.response

import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.utils.Constants
import com.google.gson.annotations.SerializedName

data class FeedbackResDto(
    @SerializedName("feedback")
    val feedbacks: String
)

fun FeedbackResDto.toFeedbacks(): Feedbacks {
    return Feedbacks(
        // version 1
//        feedbacks = feedbacks
//            .split(Constants.FEEDBACK_SEPARATOR)
//            .filter { it.isNotEmpty() }
//            .filter { it.length >= 11 }
//            .map { it.substring(7) }
////            .map { it.replace("\n", "") }

        // version 2
        feedbacks = feedbacks
            .split(Constants.FEEDBACK_SEPARATOR)
            .filter { it.isNotEmpty() }
            .filter { it.length >= 11 }
            .map {
                if (it.contains(":")) {
                    it.split(":")[1]
                } else {
                    it
                }
            }
    )
}