package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.capstone.vieweeapp.data.source.local.entity.Feedbacks
import com.capstone.vieweeapp.data.source.local.entity.ResumeDetail
import com.capstone.vieweeapp.data.source.local.entity.TextSentiment
import com.capstone.vieweeapp.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class FeedbacksTypeConverter(
    private val jsonParser: JsonParser
//    private val jsonParser: Moshi // ->Example, if project huge
) {

    @TypeConverter
    fun fromFeedbacksJson(json: String): Feedbacks{
        return jsonParser.fromJson<Feedbacks>(
            json,
            object : TypeToken<Feedbacks>(){}.type
        ) ?: Feedbacks(feedbacks = emptyList())
    }

    @TypeConverter
    fun toFeedbacksJson(feedbacks: Feedbacks): String {
        return jsonParser.toJson(
            feedbacks,
            object : TypeToken<Feedbacks>(){}.type
        ) ?: "[]"
    }
}