package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.capstone.vieweeapp.data.source.local.entity.Emotion
import com.capstone.vieweeapp.utils.parser.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class EmotionListTypeConverter(
    private val jsonParser: JsonParser
//    private val jsonParser: Moshi // ->Example, if project huge
) {

    @TypeConverter
    fun fromEmotionListJson(json: String): List<Emotion> {
        return jsonParser.fromJson<ArrayList<Emotion>>(
            json,
            object : TypeToken<ArrayList<Emotion>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toEmotionListJson(emotionList: List<Emotion>): String {
        return jsonParser.toJson(
            emotionList,
            object : TypeToken<ArrayList<Emotion>>(){}.type
        ) ?: "[]"
    }
}