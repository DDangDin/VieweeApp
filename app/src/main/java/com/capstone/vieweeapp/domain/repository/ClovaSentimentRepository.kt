package com.capstone.vieweeapp.domain.repository

import com.capstone.vieweeapp.data.source.remote.clova.dto.TextSentimentReqDto
import com.capstone.vieweeapp.data.source.remote.clova.dto.TextSentimentResDto
import com.capstone.vieweeapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ClovaSentimentRepository {

    suspend fun getClovaSentimentResult(textSentimentReqDto: TextSentimentReqDto): Flow<Resource<TextSentimentResDto>>
}