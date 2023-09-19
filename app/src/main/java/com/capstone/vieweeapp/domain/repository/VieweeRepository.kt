package com.capstone.vieweeapp.domain.repository

import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import com.capstone.vieweeapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface VieweeRepository {

    suspend fun getQuestions(createQuestionsReqDto: CreateQuestionReqDto): Flow<Resource<CreateQuestionResDto>>

    suspend fun getAnswerFeedback(feedbackReqDto: FeedbackReqDto): Flow<Resource<FeedbackResDto>>

    suspend fun getAllAnswersFeedback(feedbackReqDto: FeedbackReqDto): Flow<Resource<FeedbackResDto>>
}