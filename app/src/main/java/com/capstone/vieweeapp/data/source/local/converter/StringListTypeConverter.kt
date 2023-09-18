package com.capstone.vieweeapp.data.source.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.capstone.vieweeapp.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class StringListTypeConverter(
    private val jsonParser: JsonParser
//    private val jsonParser: Moshi // ->Example, if project huge
) {
    @TypeConverter
    fun fromStringListJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toStringListJson(stringList: List<String>): String {
        return jsonParser.toJson(
            stringList,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }
}