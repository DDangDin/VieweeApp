package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capstone.vieweeapp.data.source.local.converter.EmotionListTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.FeedbacksTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.StringListTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.TextSentimentListTypeConverter
import com.capstone.vieweeapp.data.source.local.entity.InterviewResult

@Database(
    entities = [InterviewResult::class],
    version = 1
)
@TypeConverters(
    value = [
        EmotionListTypeConverter::class,
        FeedbacksTypeConverter::class,
        StringListTypeConverter::class,
        TextSentimentListTypeConverter::class
    ]
)
abstract class InterviewResultDatabase : RoomDatabase() {

    abstract fun interviewResultDao(): InterviewResultDao
}