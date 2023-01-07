package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.repositories.Repository

class GetApiConfigurationUseCase(
    private val repository: Repository
) {

    operator fun invoke() : ApiConfigResponse {
        return repository.getApiConfiguration()
    }
}