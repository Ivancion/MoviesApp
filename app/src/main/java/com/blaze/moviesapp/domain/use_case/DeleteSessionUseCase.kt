package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.DeleteSessionResponse
import com.blaze.moviesapp.domain.repositories.AuthRepository
import com.blaze.moviesapp.other.AuthResult
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AuthResult<DeleteSessionResponse> {
        return authRepository.deleteSession()
    }
}