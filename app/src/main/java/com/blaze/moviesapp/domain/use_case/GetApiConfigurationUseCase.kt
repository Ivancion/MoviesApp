package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.repositories.Repository
import javax.inject.Inject

class GetApiConfigurationUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke() : ApiConfigResponse {
        return repository.getApiConfiguration()
    }
}