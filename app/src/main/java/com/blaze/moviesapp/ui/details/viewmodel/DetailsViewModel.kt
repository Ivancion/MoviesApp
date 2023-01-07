package com.blaze.moviesapp.ui.details.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.blaze.moviesapp.domain.models.*
import com.blaze.moviesapp.domain.use_case.*
import com.blaze.moviesapp.other.Constants.ABOUT_MOVIE
import com.blaze.moviesapp.other.Constants.CAST
import com.blaze.moviesapp.other.Constants.REVIEWS
import com.blaze.moviesapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getApiConfigurationUseCase: GetApiConfigurationUseCase,
    private val getMovieStatesUseCase: GetMovieStatesUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase
): ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieStates = MutableStateFlow<MovieState?>(null)
    val movieStates = _movieStates.asStateFlow()

    private val _serverError = MutableSharedFlow<Boolean>()
    val serverError = _serverError.asSharedFlow()

    var movieReviews: List<Author>? = null
    var movieCast: List<Cast>? = null

    val infoTabs: LiveData<List<String>> = liveData {
        emit(
            listOf(
                ABOUT_MOVIE,
                REVIEWS,
                CAST
            )
        )
    }

    private fun getMovieStates(movieId: Int) {
        viewModelScope.launch {
            getMovieStatesUseCase(movieId).collect {
                when(it) {
                    is Resource.Success -> {
                        _movieStates.value = it.data
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

    var addToWatchlistAction = false
    fun addToWatchlist() {
        viewModelScope.launch {
            _movieId?.let { movieId ->
                addToWatchlistUseCase(movieStates.value?.watchlist != true, movieId).collect {
                    when(it) {
                        is Resource.Success -> {
                            addToWatchlistAction = true
                            getMovieStates(movieId)
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

    private var _movieId: Int? = null

    fun loadDetailForMovie(movieId: Int) {
        _movieId = movieId

        getMovieDetail(movieId)
        getMovieStates(movieId)
    }

    private fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase(id).collect {
                when(it) {
                    is Resource.Success -> {
                        _movieDetail.value = it.data
                        movieReviews = it.data.reviews?.results
                        movieCast = it.data.credits?.cast
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

    fun getApiConfiguration(): ApiConfigResponse {
        return getApiConfigurationUseCase()
    }
}