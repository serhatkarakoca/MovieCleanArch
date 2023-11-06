package com.karakoca.moviecleanarch.data.di

import android.content.Context
import androidx.room.Room
import com.karakoca.moviecleanarch.data.datasource.local.MovieDao
import com.karakoca.moviecleanarch.data.datasource.local.MovieDatabase
import com.karakoca.moviecleanarch.data.remote.MovieApi
import com.karakoca.moviecleanarch.data.repository.MovieRepositoryImpl
import com.karakoca.moviecleanarch.domain.repository.MovieRepository
import com.karakoca.moviecleanarch.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient.Builder): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: MovieApi, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(api, movieDao)
    }

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(appContext, MovieDatabase::class.java, Constant.APP_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.dao()
    }
}