package com.blaze.moviesapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blaze.moviesapp.domain.use_case.GetSessionIdUseCase
import com.blaze.moviesapp.domain.use_case.LoadApiConfigurationUseCase
import com.blaze.moviesapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadApiConfigurationUseCase: LoadApiConfigurationUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase
) : ViewModel() {

    private val _isLoggedIn = MutableSharedFlow<Boolean>()
    val isLoggedIn = _isLoggedIn.asSharedFlow()

    private val _serverError = MutableSharedFlow<Boolean>()
    val serverError = _serverError.asSharedFlow()

    fun loadApiConfig() {
        viewModelScope.launch {
            loadApiConfigurationUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        checkForUserLogin()
                    }
                    is Resource.Error -> {
                        _serverError.emit(true)
                    }
                    is Resource.LoadingState -> {}
                }
            }
        }
    }

    private suspend fun checkForUserLogin() {
        val sessionId = getSessionIdUseCase()
        _isLoggedIn.emit(sessionId.isNotEmpty())
    }
}