package com.blaze.moviesapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.use_case.DeleteSessionUseCase
import com.blaze.moviesapp.domain.use_case.GetApiConfigurationUseCase
import com.blaze.moviesapp.domain.use_case.GetPagerHomeScreenUseCase
import com.blaze.moviesapp.domain.use_case.GetTrendingMoviesUseCase
import com.blaze.moviesapp.other.Constants.NOW_PLAYING
import com.blaze.moviesapp.other.Constants.POPULAR
import com.blaze.moviesapp.other.Constants.TOP_RATED
import com.blaze.moviesapp.other.Constants.UPCOMING
import com.blaze.moviesapp.other.MovieCategory
import com.blaze.moviesapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getApiConfigurationUseCase: GetApiConfigurationUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPagerHomeScreenUseCase: GetPagerHomeScreenUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase
): ViewModel() {

    private val _categoryMoviesPageResponse = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val categoryMoviesPageResponse = _categoryMoviesPageResponse.asStateFlow()

    private val _serverError = MutableSharedFlow<Boolean>()
    val serverError = _serverError.asSharedFlow()

    private val _isSessionDeleted = MutableSharedFlow<Boolean>()
    val isSessionDeleted = _isSessionDeleted.asSharedFlow()

    val trendingMovies: LiveData<List<Movie>> = liveData {
        getTrendingMoviesUseCase().collect {
            when(it) {
                is Resource.Success -> {
                    emit(it.data.results.take(10))
                }
                is Resource.Error -> {
                    _serverError.emit(true)
                }
                is Resource.LoadingState -> {}
            }
        }
    }

    fun getMoviesByCategory(category: MovieCategory) {
        viewModelScope.launch {
            getPagerHomeScreenUseCase(
                category
            ).cachedIn(viewModelScope).collect {
                _categoryMoviesPageResponse.value = it
            }
        }
    }

    fun getApiConfiguration(): ApiConfigResponse {
        return getApiConfigurationUseCase()
    }

    val categories: LiveData<List<String>> = liveData {
        emit(listOf(
            NOW_PLAYING,
            UPCOMING,
            TOP_RATED,
            POPULAR
        ))
    }

    fun deleteSession() {
        viewModelScope.launch {
            deleteSessionUseCase().collect {
                when(it) {
                    is Resource.Success -> {
                        if(it.data.success == true) {
                            _isSessionDeleted.emit(true)
                        }
                    }
                    is Resource.Error -> {
                        Log.d("Error", it.exception)
                        _serverError.emit(true)
                    }
                    is Resource.LoadingState -> {}
                }
            }
        }
    }
}