package com.capstone.vieweeapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.capstone.vieweeapp.data.repository.ClovaSentimentRepositoryImpl
import com.capstone.vieweeapp.data.repository.InterviewResultRepositoryImpl
import com.capstone.vieweeapp.data.repository.ResumeRepositoryImpl
import com.capstone.vieweeapp.data.repository.UserRepositoryImpl
import com.capstone.vieweeapp.data.repository.VieweeRepositoryImpl
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
import com.capstone.vieweeapp.data.source.remote.clova.dto.ClovaSentimentApi
import com.capstone.vieweeapp.data.source.remote.viewee.dto.VieweeApi
import com.capstone.vieweeapp.data.source.remote.viewee.dto.VieweeMockApi
import com.capstone.vieweeapp.domain.repository.ClovaSentimentRepository
import com.capstone.vieweeapp.domain.repository.InterviewResultRepository
import com.capstone.vieweeapp.domain.repository.ResumeRepository
import com.capstone.vieweeapp.domain.repository.UserRepository
import com.capstone.vieweeapp.domain.repository.VieweeRepository
import com.capstone.vieweeapp.utils.Constants
import com.capstone.vieweeapp.utils.parser.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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

    // API
    @Provides
    @Singleton
    fun provideClovaApi(): ClovaSentimentApi {
        fun String?.isJsonObject(): Boolean {
            return this?.startsWith("{") == true && this.endsWith("}")
        }

        fun String?.isJsonArray(): Boolean {
            return this?.startsWith("[") == true && this.endsWith("]")
        }

        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                else -> {
                    try {
                        Log.d("Retrofit_Log", JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d("Retrofit_Log", message)
                    }
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.CLOVA_SENTIMENT_BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClovaSentimentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVieweeMockApi(): VieweeMockApi {
        fun String?.isJsonObject(): Boolean {
            return this?.startsWith("{") == true && this.endsWith("}")
        }

        fun String?.isJsonArray(): Boolean {
            return this?.startsWith("[") == true && this.endsWith("]")
        }

        val client = OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                else -> {
                    try {
                        Log.d("Retrofit_Log", JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d("Retrofit_Log", message)
                    }
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.VIEWEE_MOCK_SERVER_BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VieweeMockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVieweeApi(): VieweeApi {
        fun String?.isJsonObject(): Boolean {
            return this?.startsWith("{") == true && this.endsWith("}")
        }

        fun String?.isJsonArray(): Boolean {
            return this?.startsWith("[") == true && this.endsWith("]")
        }

        val client = OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                else -> {
                    try {
                        Log.d("Retrofit_Log", JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d("Retrofit_Log", message)
                    }
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.VIEWEE_REAL_SERVER_BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VieweeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVieweeRepository(
        api: VieweeApi,
        mockApi: VieweeMockApi
    ): VieweeRepository {
        return VieweeRepositoryImpl(api, mockApi)
    }

    @Provides
    @Singleton
    fun provideClovaSentimentRepository(api: ClovaSentimentApi): ClovaSentimentRepository {
        return ClovaSentimentRepositoryImpl(api)
    }
}