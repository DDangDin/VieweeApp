package com.capstone.vieweeapp.data.source.remote.viewee.dto.request

import com.google.gson.annotations.SerializedName

data class CreateQuestionReqDto(
    val birth: String,
    val career: String,
    val certificate: String,
    val education: String,
    val name: String,
    val resume: String,
    @SerializedName("support_job")
    val supportJob: String
)
