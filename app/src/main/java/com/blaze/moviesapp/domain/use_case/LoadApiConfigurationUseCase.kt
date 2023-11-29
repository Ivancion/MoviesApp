package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadApiConfigurationUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke() : Flow<Resource<Unit>> {
        return moviesRepository.loadApiConfiguration()
    }
}