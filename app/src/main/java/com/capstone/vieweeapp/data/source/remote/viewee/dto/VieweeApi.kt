package com.capstone.vieweeapp.data.source.remote.viewee.dto

import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VieweeApi {

    @POST("gpt/setting")
    fun getQuestions(
        @Body createQuestionsReq: CreateQuestionReqDto
    ): Call<CreateQuestionResDto>

    @POST("gpt/answer_feedback")
    fun getAnswerFeedback(
        @Body feedbackReq: FeedbackReqDto
    ): Call<FeedbackResDto>

    @POST("gpt/overrall_feedback")
    fun getAllAnswersFeedback(
        @Body feedbackReq: FeedbackReqDto
    ): Call<FeedbackResDto>
}