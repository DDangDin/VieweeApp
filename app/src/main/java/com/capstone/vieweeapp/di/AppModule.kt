package com.capstone.vieweeapp.di

import android.content.Context
import androidx.room.Room
import com.capstone.vieweeapp.data.repository.InterviewResultRepositoryImpl
import com.capstone.vieweeapp.data.repository.ResumeRepositoryImpl
import com.capstone.vieweeapp.data.repository.UserRepositoryImpl
import com.capstone.vieweeapp.data.source.local.converter.EmotionListTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.FeedbacksTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.ResumeDetailTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.StringListTypeConverter
import com.capstone.vieweeapp.data.source.local.converter.TextSentimentListTypeConverter
import com.capstone.vieweeapp.data.source.local.db.InterviewResultDao
import com.capstone.vieweeapp.data.source.local.db.InterviewResultDatabase
import com.capstone.vieweeapp.data.source.local.db.ResumeDao
import com.capstone.vieweeapp.data.source.local.db.ResumeDatabase
import com.capstone.vieweeapp.data.source.local.db.UserDao
import com.capstone.vieweeapp.data.source.local.db.UserDatabase
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.domain.repository.UserRepository
import com.capstone.vieweeapp.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Room Database
    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(appDatabase: UserDatabase) = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideResumeDatabase(@ApplicationContext context: Context): ResumeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ResumeDatabase::class.java,
            "resume_db"
        ).addTypeConverter(ResumeDetailTypeConverter(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideResumeDao(appDatabase: ResumeDatabase) = appDatabase.resumeDao()

    @Provides
    @Singleton
    fun provideInterviewResultDatabase(@ApplicationContext context: Context): InterviewResultDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            InterviewResultDatabase::class.java,
            "interview_result_db"
        ).addTypeConverter(StringListTypeConverter(GsonParser(Gson())))
            .addTypeConverter(TextSentimentListTypeConverter(GsonParser(Gson())))
            .addTypeConverter(EmotionListTypeConverter(GsonParser(Gson())))
            .addTypeConverter(FeedbacksTypeConverter(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideInterviewResultDao(appDatabase: InterviewResultDatabase) = appDatabase.interviewResultDao()

    // Repositories
    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao): UserRepository {
        return UserRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideResumeRepository(dao: ResumeDao): ResumeRepository {
        return ResumeRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideInterviewResultRepository(dao: InterviewResultDao): InterviewResultRepository {
        return InterviewResultRepositoryImpl(dao)
    }
}