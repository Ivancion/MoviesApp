package com.blaze.moviesapp.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.use_case.GetApiConfigurationUseCase
import com.blaze.moviesapp.domain.use_case.GetPagerSearchScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getApiConfigurationUseCase: GetApiConfigurationUseCase,
    private val getPagerSearchScreenUseCase: GetPagerSearchScreenUseCase
): ViewModel() {

    private val _searchMoviesPageResponse = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchMoviesPageResponse = _searchMoviesPageResponse.asStateFlow()

    private val _isEmptyResponse = MutableStateFlow(true)
    val isEmptyResponse = _isEmptyResponse.asStateFlow()

    private var searchJob: Job? = null
    fun searchMovies(query: String) {
        searchJob?.cancel()
        if(query.isEmpty())
            return
        searchJob = viewModelScope.launch {
            delay(500)
            getPagerSearchScreenUseCase(
                query
            ) {
                _isEmptyResponse.value = it
            }.cachedIn(viewModelScope).collect {
                _searchMoviesPageResponse.value = it
            }
        }
    }

    fun getApiConfiguration(): ApiConfigResponse {
        return getApiConfigurationUseCase()
    }
}