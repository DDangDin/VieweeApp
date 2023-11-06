package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.data.source.remote.viewee.dto.VieweeApi
import com.capstone.vieweeapp.data.source.remote.viewee.dto.VieweeMockApi
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.CreateQuestionReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.FeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.ReFeedbackReqDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.request.TempRequest
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.CreateQuestionResDto
import com.capstone.vieweeapp.data.source.remote.viewee.dto.response.FeedbackResDto
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException
import java.security.PrivateKey

class VieweeRepositoryImpl(
    private val api: VieweeApi,
    private val mockApi: VieweeMockApi
): VieweeRepository {

    override suspend fun getQuestionsFromMock(createQuestionReqDto: CreateQuestionReqDto): Flow<Resource<CreateQuestionResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = mockApi.getQuestions(createQuestionReqDto)
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }

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

    override suspend fun getReAnswerFeedback1(feedbackReqDto: ReFeedbackReqDto, index: Int): Flow<Resource<FeedbackResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = when(index) {
                0 -> api.getReAnswerFeedback1(feedbackReqDto)
                1 -> api.getReAnswerFeedback2(feedbackReqDto)
                2 -> api.getReAnswerFeedback3(feedbackReqDto)
                3 -> api.getReAnswerFeedback4(feedbackReqDto)
                4 -> api.getReAnswerFeedback5(feedbackReqDto)
                else -> api.getReAnswerFeedback1(feedbackReqDto)
            }
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }

    override suspend fun getReInterviewFeedback(feedbackReqDto: ReFeedbackReqDto): Flow<Resource<FeedbackResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = api.getReInterviewFeedback(feedbackReqDto)
            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))
        }
    }

    override suspend fun requestServerForReInterview(tempRequest: TempRequest) {
        api.requestServerForReInterview(tempRequest)
    }
}