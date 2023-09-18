package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.data.source.remote.viewee.dto.VieweeApi
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException

class VieweeRepositoryImpl(
    private val api: VieweeApi
): VieweeRepository {

    override suspend fun getQuestions(createQuestionReqDto: CreateQuestionReqDto): Flow<Resource<CreateQuestionResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = api.getQuestions(createQuestionReqDto)
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }

    override suspend fun getAnswerFeedback(feedbackReqDto: FeedbackReqDto): Flow<Resource<FeedbackResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = api.getAnswerFeedback(feedbackReqDto)
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }

    override suspend fun getAllAnswersFeedback(feedbackReqDto: FeedbackReqDto): Flow<Resource<FeedbackResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = api.getAllAnswersFeedback(feedbackReqDto)
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }
}