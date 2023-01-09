package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.Repository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): String {
        return repository.getSessionId()
    }
}