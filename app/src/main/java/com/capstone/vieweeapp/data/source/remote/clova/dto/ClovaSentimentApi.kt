package com.capstone.vieweeapp.data.source.remote.clova.dto

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ClovaSentimentApi {

    @POST("sentiment-analysis/v1/analyze")
    fun getClovaSentiment(
        @Header("X-NCP-APIGW-API-KEY-ID") key_id: String,
        @Header("X-NCP-APIGW-API-KEY") key: String,
        @Header("Content-Type") contentType: String,
        @Body textSentimentReqDto: TextSentimentReqDto
    ): Call<TextSentimentResDto>
}