package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.capstone.vieweeapp.data.source.local.entity.ResumeDetail
import com.capstone.vieweeapp.utils.parser.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ResumeDetailTypeConverter(
    private val jsonParser: JsonParser
//    private val jsonParser: Moshi // ->Example, if project huge
) {

    @TypeConverter
    fun fromResumeDetailJson(json: String): ResumeDetail{
        return jsonParser.fromJson<ResumeDetail>(
            json,
            object : TypeToken<ResumeDetail>(){}.type
        ) ?: ResumeDetail(emptyList(),"", emptyList(),"")
    }

    @TypeConverter
    fun toResumeDetailJson(resumeDetail: ResumeDetail): String {
        return jsonParser.toJson(
            resumeDetail,
            object : TypeToken<ResumeDetail>(){}.type
        ) ?: "[]"
    }
}