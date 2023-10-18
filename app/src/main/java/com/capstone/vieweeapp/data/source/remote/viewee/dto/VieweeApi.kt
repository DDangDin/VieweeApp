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

interface VieweeApi {

    // --- 임시 ---
    @POST("gpt/overrall_feedback")
    fun getAllAnswersFeedback(
        @Body feedbackReq: FeedbackReqDto
    ): Call<FeedbackResDto>
    // ---

    @POST("gpt/setting")
    fun getQuestions(
        @Body createQuestionsReq: CreateQuestionReqDto
    ): Call<CreateQuestionResDto>

    @POST("gpt/interview/feedback")
    fun getAnswerFeedback(
        @Body feedbackReq: FeedbackReqDto
    ): Call<FeedbackResDto>

    @POST("gpt/interview/feedback/re_answer_feedback_1")
    fun getReAnswerFeedback1(
        @Body feedbackReq: ReFeedbackReqDto
    ): Call<FeedbackResDto>

    // 재면접 피드백 주는
    @POST("gpt/re_Interview/feedback")
    fun getReInterviewFeedback(
        @Body feedbackReq: ReFeedbackReqDto
    ): Call<FeedbackResDto>

    // 재면접 진행 및 세팅
    @POST("gpt/re_Interview")
    fun requestServerForReInterview(
        @Body tempRequest: TempRequest
    ): Call<Unit>
}