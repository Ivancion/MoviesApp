package com.blaze.moviesapp.di

import android.content.Context
import android.content.SharedPreferences
import com.blaze.moviesapp.data.local.*
import com.blaze.moviesapp.data.remote.MovieDBApi
import com.blaze.moviesapp.data.repositories.RepositoryImpl
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.BASE_URL
import com.blaze.moviesapp.other.Constants.SHARED_PREFS_NAME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    fun provideMovieDBApi(): MovieDBApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRequestToken(): IRequestToken {
        return RequestToken()
    }

    @Singleton
    @Provides
    fun provideSessionId(): ISessionId {
        return SessionId()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSystemPreferences(
        sharedPreferences: SharedPreferences
    ) : ISystemPreferences {
        return SystemPreferences(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideApiConfiguration(): IApiConfiguration {
        return ApiConfiguration()
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: MovieDBApi,
        iSystemPreferences: ISystemPreferences,
        iSessionId: ISessionId,
        iRequestToken: IRequestToken,
        iApiConfiguration: IApiConfiguration
    ) : Repository {
        return RepositoryImpl(
            api = api,
            iRequestToken = iRequestToken,
            iSessionId = iSessionId,
            iSystemPreferences = iSystemPreferences,
            iApiConfiguration = iApiConfiguration
        )
    }


}