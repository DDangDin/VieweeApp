package com.capstone.vieweeapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.capstone.vieweeapp.data.source.local.converter.ResumeDetailTypeConverter
import com.capstone.vieweeapp.data.source.local.entity.Resume

@Database(
    entities = [Resume::class],
    version = 1
)
@TypeConverters(ResumeDetailTypeConverter::class)
abstract class ResumeDatabase: RoomDatabase() {

    abstract fun resumeDao(): ResumeDao
}