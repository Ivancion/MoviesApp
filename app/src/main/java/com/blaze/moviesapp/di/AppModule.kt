package com.blaze.moviesapp.di

import android.content.Context
import android.content.SharedPreferences
import com.blaze.moviesapp.data.local.*
import com.blaze.moviesapp.data.remote.LoginService
import com.blaze.moviesapp.data.remote.MovieService
import com.blaze.moviesapp.data.repositories.AuthRepositoryImpl
import com.blaze.moviesapp.data.repositories.MoviesRepositoryImpl
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.BASE_URL
import com.blaze.moviesapp.other.Constants.SHARED_PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieDBApi(): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginApi(): LoginService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
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
        api: MovieService,
        iApiConfiguration: IApiConfiguration
    ) : MoviesRepository {
        return MoviesRepositoryImpl(
            movieService = api,
            iApiConfiguration = iApiConfiguration
        )
    }

    @Singleton
    @Provides
    fun provideLoginRepository(
        api: LoginService,
        iSessionId: ISessionId,
        iSystemPreferences: ISystemPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(
            loginService = api,
            iSessionId = iSessionId,
            systemPreferences = iSystemPreferences
        )
    }


}