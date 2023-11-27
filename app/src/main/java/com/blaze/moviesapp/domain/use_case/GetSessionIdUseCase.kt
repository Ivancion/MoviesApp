package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.AuthRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): String {
        return authRepository.getSessionId()
    }
}