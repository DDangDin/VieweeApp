package com.capstone.vieweeapp.data.repository

import com.capstone.vieweeapp.BuildConfig
import com.capstone.vieweeapp.data.source.remote.clova.dto.ClovaSentimentApi
import com.capstone.vieweeapp.data.source.remote.clova.dto.TextSentimentReqDto
import com.capstone.vieweeapp.data.source.remote.clova.dto.TextSentimentResDto
import com.capstone.vieweeapp.domain.repository.ClovaSentimentRepository
import com.capstone.vieweeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException

class ClovaSentimentRepositoryImpl(
    private val api: ClovaSentimentApi
): ClovaSentimentRepository {

    override suspend fun getClovaSentimentResult(textSentimentReqDto: TextSentimentReqDto): Flow<Resource<TextSentimentResDto>> = flow {
        emit(Resource.Loading())

        try {
            val call = api.getClovaSentiment(
                key_id = BuildConfig.CLOVA_SENTIMENT_API_KEY_ID,
                key = BuildConfig.CLOVA_SENTIMENT_API_KEY,
                contentType = "application/json",
                textSentimentReqDto = textSentimentReqDto
            )

            val response = call.await()
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "internet connection error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: ""))
        }
    }
}