package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

//@ProvidedTypeConverter
//class Converter(
//    private val jsonParser: JsonParser
////    private val jsonParser: Moshi // ->Example, if project huge
//) {
//    @TypeConverter
//    fun fromMeaningsJson(json: String): List<ResumeText> {
//        return jsonParser.fromJson<ArrayList<ResumeText>>(
//            json,
//            object : TypeToken<ArrayList<ResumeText>>(){}.type
//        ) ?: emptyList()
//    }
//
//    @TypeConverter
//    fun toMeaningsJson(meaning: List<ResumeText>): String {
//        return jsonParser.toJson(
//            meaning,
//            object : TypeToken<ArrayList<ResumeText>>(){}.type
//        ) ?: "[]"
//    }
//}