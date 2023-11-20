package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.LoginRepository
import com.blaze.moviesapp.other.Constants.INVALID_LOGIN_DATA
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(
        username: String,
        password: String,
        rememberUser: Boolean
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.LoadingState)
            try {
                val requestToken = loginRepository.getRequestToken()
                val loginRequestToken = loginRepository.createSessionWithLogin(
                    username,
                    password,
                    requestToken.requestToken!!
                )
                loginRepository.createSessionId(loginRequestToken.requestToken!!)
                if(rememberUser) {
                    loginRepository.saveSessionId()
                }
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                if(e.message?.contains("401") == true) {
                    emit(Resource.Error(INVALID_LOGIN_DATA))
                } else {
                    emit(Resource.Error(e.localizedMessage ?: e.message ?: UNKNOWN_ERROR))
                }
            }
        }
    }
}