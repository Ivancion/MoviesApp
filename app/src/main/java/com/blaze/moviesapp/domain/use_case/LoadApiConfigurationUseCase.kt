package com.blaze.moviesapp.domain.use_case

import com.blaze.moviesapp.domain.repositories.MoviesRepository
import com.blaze.moviesapp.other.Constants.UNKNOWN_ERROR
import com.blaze.moviesapp.other.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadApiConfigurationUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke() : Flow<Resource<Unit>> {
        return flow {
            emit(Resource.LoadingState)
            runCatching {
                moviesRepository.loadApiConfiguration()
            }.onSuccess {
                emit(Resource.Success(Unit))
            }.onFailure {
                emit(Resource.Error(it.localizedMessage ?: it.message ?: UNKNOWN_ERROR))
            }
        }
    }
}