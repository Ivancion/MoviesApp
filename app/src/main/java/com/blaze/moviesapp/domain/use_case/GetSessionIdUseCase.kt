package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.LoginRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    operator fun invoke(): String {
        return loginRepository.getSessionId()
    }
}