package com.blaze.moviesapp.domain.use_case

import android.util.Log
import com.blaze.moviesapp.domain.repositories.Repository
import com.blaze.moviesapp.other.Constants.INVALID_LOGIN_DATA
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(username: String, password: String, rememberUser: Boolean) : Flow<Resource<Unit>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                repository.getRequestToken()
            }.onSuccess {
                runCatching {
                    repository.createSessionWithLogin(username, password)
                }.onSuccess {
                    runCatching {
                        repository.createSessionId()
                    }.onSuccess {
                        if(rememberUser)
                            repository.saveSessionId()
                        emit(Resource.Success(Unit))
                    }.onFailure {
                        emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
                    }
                }.onFailure {
                    if(it.message?.trim() == "HTTP 401") {
                        emit(Resource.Error(INVALID_LOGIN_DATA))
                    } else {
                        emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
                    }
                }
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}