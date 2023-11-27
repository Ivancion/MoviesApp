package com.blaze.moviesapp.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.models.Movie
import com.blaze.moviesapp.domain.use_case.GetApiConfigurationUseCase
import com.blaze.moviesapp.domain.use_case.GetWatchlistPagingFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistPagingFlowUseCase: GetWatchlistPagingFlowUseCase,
    private val getApiConfigurationUseCase: GetApiConfigurationUseCase
): ViewModel() {

    private val _watchlistPagingData = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val watchlistPagingData = _watchlistPagingData.asStateFlow()

    private val _isEmptyResponse = MutableStateFlow(false)
    val isEmptyResponse = _isEmptyResponse.asStateFlow()

    fun getWatchlist() {
        viewModelScope.launch {
            getWatchlistPagingFlowUseCase {
                _isEmptyResponse.value = it
            }.cachedIn(viewModelScope).collect {
                _watchlistPagingData.value = it
            }
        }
    }

    fun getApiConfiguration() : ApiConfigResponse =
        getApiConfigurationUseCase()
}