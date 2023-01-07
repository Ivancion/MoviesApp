package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.Repository

class GetSessionIdUseCase(
    private val repository: Repository
) {

    operator fun invoke(): String {
        return repository.getSessionId()
    }
}