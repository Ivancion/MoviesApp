package com.blaze.moviesapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blaze.moviesapp.domain.use_case.LoginUserUseCase
import com.blaze.moviesapp.other.Constants.INVALID_LOGIN_DATA
import com.blaze.moviesapp.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
): ViewModel() {

    private val _snackbarErrorMessage = MutableSharedFlow<String>()
    val snackbarErrorMessage = _snackbarErrorMessage.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess = _isSuccess.asSharedFlow()

    private val _serverError = MutableSharedFlow<Boolean>()
    val serverError = _serverError.asSharedFlow()

    fun login(username: String, password: String, rememberUser: Boolean) {

        viewModelScope.launch {
            if(username.isEmpty() || password.isEmpty()) {
                _snackbarErrorMessage.emit("All fields must be filled")
                return@launch
            }

            loginUserUseCase(username, password, rememberUser).collect {
                when(it) {
                    is Resource.Success -> {
                        _isSuccess.emit(true)
                        _loading.value = false
                    }
                    is Resource.Error -> {
                        if(it.exception == INVALID_LOGIN_DATA) {
                            _snackbarErrorMessage.emit(it.exception)
                        } else {
                            _serverError.emit(true)
                        }
                        _loading.value = false
                    }
                    is Resource.LoadingState -> {
                        _loading.value = true
                    }
                }
            }
        }
    }
}