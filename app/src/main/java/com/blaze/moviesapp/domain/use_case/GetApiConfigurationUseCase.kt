package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.models.ApiConfigResponse
import com.blaze.moviesapp.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetApiConfigurationUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke() : ApiConfigResponse {
        return moviesRepository.getApiConfiguration()
    }
}