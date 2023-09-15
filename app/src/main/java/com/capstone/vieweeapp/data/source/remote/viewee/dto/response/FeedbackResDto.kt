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
        feedbacks = feedbacks.split(Constants.FEEDBACK_SEPARATOR)
    )
}