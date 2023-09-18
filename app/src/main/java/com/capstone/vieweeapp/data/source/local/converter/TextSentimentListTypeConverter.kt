package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class TextSentimentListTypeConverter(
    private val jsonParser: JsonParser
//    private val jsonParser: Moshi // ->Example, if project huge
) {

    @TypeConverter
    fun fromTextSentimentListJson(json: String): List<TextSentiment> {
        return jsonParser.fromJson<ArrayList<TextSentiment>>(
            json,
            object : TypeToken<ArrayList<TextSentiment>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toTextSentimentListJson(textSentimentList: List<TextSentiment>): String {
        return jsonParser.toJson(
            textSentimentList,
            object : TypeToken<ArrayList<TextSentiment>>(){}.type
        ) ?: "[]"
    }
}