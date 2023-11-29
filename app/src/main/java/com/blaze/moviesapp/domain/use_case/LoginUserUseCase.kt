package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.other.AuthResult
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        username: String,
        password: String,
        rememberUser: Boolean
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.LoadingState)
            val requestTokenResponse = authRepository.getRequestToken()
            when(requestTokenResponse) {
                is AuthResult.Success -> {
                    val loginRequestTokenResponse = authRepository.createSessionWithLogin(
                        username,
                        password,
                        requestTokenResponse.data.requestToken!!
                    )
                    when(loginRequestTokenResponse) {
                        is AuthResult.Success -> {
                            val createSessionResponse =
                                authRepository.createSessionId(loginRequestTokenResponse.data.requestToken!!)
                            when(createSessionResponse) {
                                is AuthResult.Success -> {
                                    if(rememberUser) {
                                        authRepository.saveSessionId()
                                    }
                                    emit(Resource.Success(Unit))
                                }
                                is AuthResult.Error -> {
                                    emit(Resource.Error(createSessionResponse.message))
                                }
                            }
                        }
                        is AuthResult.Error -> {
                            emit(Resource.Error(loginRequestTokenResponse.message))
                        }
                    }
                }
                is AuthResult.Error -> {
                    emit(Resource.Error(requestTokenResponse.message))
                }
            }
        }
    }
}