package com.capstone.vieweeapp.data.source.remote.viewee.dto

import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.ReFeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.TempRequest
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VieweeMockApi {

    @POST("gpt/setting")
    fun getQuestions(
        @Body createQuestionsReq: CreateQuestionReqDto
    ): Call<CreateQuestionResDto>

}